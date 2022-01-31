package com.uszkaisandor.mealdeas.feature.recipe_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.uszkaisandor.mealdeas.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject @JvmOverloads constructor(
    repository: RecipeRepository
) : ViewModel() {

    private val queryString = MutableStateFlow("")

    private val _recipeList = repository.getRecipesPaged(queryString.value, false)
    val recipeList = _recipeList.cachedIn(viewModelScope)

}