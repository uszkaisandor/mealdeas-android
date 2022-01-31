package com.uszkaisandor.mealdeas.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val recipeId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
