package com.uszkaisandor.mealdeas.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.uszkaisandor.mealdeas.data.entities.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<Recipe>)

    @Query("SELECT * FROM recipes WHERE name LIKE :queryString ORDER BY name ASC")
    fun reposByName(queryString: String): PagingSource<Int, Recipe>

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("DELETE FROM recipes")
    suspend fun deleteAllRecipes()

}