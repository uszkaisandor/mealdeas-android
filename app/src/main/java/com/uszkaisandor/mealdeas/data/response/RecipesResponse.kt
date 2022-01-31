package com.uszkaisandor.mealdeas.data.response

import com.google.gson.annotations.SerializedName
import com.uszkaisandor.mealdeas.data.RecipeDto

data class RecipesResponse(
    @SerializedName("rows")
    val recipes: List<RecipeDto>,
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int
)