package com.uszkaisandor.mealdeas.feature.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uszkaisandor.mealdeas.data.RecipeRepository
import com.uszkaisandor.mealdeas.data.entities.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject @JvmOverloads constructor(
    val repository: RecipeRepository
) :
    ViewModel() {

    private val _recipeList = MutableStateFlow<List<Recipe>?>(null)
    val recipeList = _recipeList.asStateFlow()

    fun doRefresh(forceRefresh: Boolean) {
        viewModelScope.launch {
            repository.getRecipes().collect { recipes ->
                _recipeList.value = recipes
            }
        }
    }


}