package com.uszkaisandor.mealdeas.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.uszkaisandor.mealdeas.api.ClientPropertiesInterceptor
import com.uszkaisandor.mealdeas.api.MealdeasApi
import com.uszkaisandor.mealdeas.data.entities.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepository
@Inject
constructor(
    private val mealdeasApi: MealdeasApi,
    private val mealdeasDb: MealdeasDatabase,
    private val clientPropertiesInterceptor: ClientPropertiesInterceptor
) {
    private val recipeDao = mealdeasDb.recipeDao()

    fun getRecipesPaged(
        query: String,
        refreshOnInit: Boolean
    ): Flow<PagingData<Recipe>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 200
            ),
            remoteMediator = RecipeRemoteMediator(
                query = query,
                service = mealdeasApi,
                mealdeasDb = mealdeasDb
            ),
            pagingSourceFactory = {
                mealdeasDb.recipeDao().reposByName("%" + query + "%")
            }
        ).flow


}