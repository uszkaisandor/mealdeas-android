package com.uszkaisandor.mealdeas.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.uszkaisandor.mealdeas.api.MealdeasApi
import com.uszkaisandor.mealdeas.data.entities.Recipe
import com.uszkaisandor.mealdeas.data.entities.RemoteKeys
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RecipeRemoteMediator(
    private val query: String,
    private val service: MealdeasApi,
    private val mealdeasDb: MealdeasDatabase
) : RemoteMediator<Int, Recipe>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Recipe>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MEALDEAS_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        val apiQuery = query

        try {
            val apiResponse = service.getRecipes(apiQuery, page, state.config.pageSize)

            val recipes = apiResponse.recipes
            val endOfPaginationReached = recipes.isEmpty()
            mealdeasDb.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    mealdeasDb.remoteKeysDao().clearRemoteKeys()
                    mealdeasDb.recipeDao().deleteAllRecipes()
                }
                val prevKey = if (page == MEALDEAS_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = recipes.map {
                    RemoteKeys(recipeId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                mealdeasDb.remoteKeysDao().insertAll(keys)
                mealdeasDb.recipeDao().insertRecipes(recipes.map {
                    Recipe(
                        mongoId = it.id,
                        imageUri = it.imageUri,
                        name = it.name,
                        ingredients = it.ingredients
                    )
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Recipe>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { recipe ->
                // Get the remote keys of the last item retrieved
                mealdeasDb.remoteKeysDao().remoteKeysRecipeId(recipe.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Recipe>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                mealdeasDb.remoteKeysDao().remoteKeysRecipeId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Recipe>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                mealdeasDb.remoteKeysDao().remoteKeysRecipeId(repoId)
            }
        }
    }


    companion object {
        val MEALDEAS_STARTING_PAGE_INDEX = 1
    }
}
