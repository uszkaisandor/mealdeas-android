package com.uszkaisandor.mealdeas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("_id")
    val mongoId: String,
    val name: String,
    val imageUri: String,
    val ingredients: List<Ingredient>?
)