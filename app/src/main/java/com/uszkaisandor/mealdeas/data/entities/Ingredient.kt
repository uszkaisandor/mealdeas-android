package com.uszkaisandor.mealdeas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
)