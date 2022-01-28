package com.uszkaisandor.mealdeas.util.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.uszkaisandor.mealdeas.data.entities.Ingredient
import java.lang.reflect.Type
import javax.inject.Inject


class Converters {

    @Inject
    lateinit var gson: Gson

    @TypeConverter
    fun fromIngredientListList(ingredient: List<Ingredient?>?): String? {
        if (ingredient == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Ingredient?>?>() {}.type
        return gson.toJson(ingredient, type)
    }

    @TypeConverter
    fun toIngredientList(ingredientString: String?): List<Ingredient>? {
        if (ingredientString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Ingredient?>?>() {}.type
        return gson.fromJson<List<Ingredient>>(ingredientString, type)
    }

}
