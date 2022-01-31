package com.uszkaisandor.mealdeas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uszkaisandor.mealdeas.data.dao.RecipeDao
import com.uszkaisandor.mealdeas.data.dao.RemoteKeysDao
import com.uszkaisandor.mealdeas.data.entities.Ingredient
import com.uszkaisandor.mealdeas.data.entities.Recipe
import com.uszkaisandor.mealdeas.data.entities.RemoteKeys
import com.uszkaisandor.mealdeas.util.typeconverters.Converters

@Database(
    entities = [
        Recipe::class,
        Ingredient::class,
        RemoteKeys::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MealdeasDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}