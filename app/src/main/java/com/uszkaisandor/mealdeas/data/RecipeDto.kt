package com.uszkaisandor.mealdeas.data

import com.google.gson.annotations.SerializedName
import com.uszkaisandor.mealdeas.data.entities.Ingredient

data class RecipeDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val imageUri: String,
    val ingredients: List<Ingredient>?
)