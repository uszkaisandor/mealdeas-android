package com.uszkaisandor.mealdeas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uszkaisandor.mealdeas.data.entities.Ingredient
import com.uszkaisandor.mealdeas.data.entities.Recipe
import com.uszkaisandor.mealdeas.util.typeconverters.Converters

@Database(
    entities = [
        Recipe::class,
        Ingredient::class
    ], version = 2
)
@TypeConverters(Converters::class)
abstract class MealDatabase : RoomDatabase()