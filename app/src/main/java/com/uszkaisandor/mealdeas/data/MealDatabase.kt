package com.uszkaisandor.mealdeas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uszkaisandor.mealdeas.data.entities.Recipe

@Database(
    entities = [
        Recipe::class,
    ], version = 1
)
abstract class MealDatabase : RoomDatabase() {
}