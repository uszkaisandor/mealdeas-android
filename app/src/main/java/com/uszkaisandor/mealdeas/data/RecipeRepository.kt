package com.uszkaisandor.mealdeas.data


import com.uszkaisandor.mealdeas.data.entities.Ingredient
import com.uszkaisandor.mealdeas.data.entities.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

// todo implement later, this is just mock for now
class RecipeRepository
@Inject
constructor() {

    val recipe = Recipe(
        id = Random.nextInt(),
        name = "Ramen",
        imageUri = "https://images.pexels.com/photos/2664216/pexels-photo-2664216.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
        ingredients = listOf(
            Ingredient(
                id = Random.nextInt(),
                name = "eggs"
            ),
            Ingredient(
                id = Random.nextInt(),
                name = "meat"
            ),
            Ingredient(
                id = Random.nextInt(),
                name = "pasta"
            ),
            Ingredient(
                id = Random.nextInt(),
                name = "kimchi"
            ),
        )
    )

    fun getRecipes(): Flow<List<Recipe>> {
        return flow {
            emit(
                listOf(
                    recipe,
                    recipe,
                    recipe,
                    recipe
                )
            )
        }.debounce(500)
    }
}