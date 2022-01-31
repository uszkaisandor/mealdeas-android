package com.uszkaisandor.mealdeas.api

import com.uszkaisandor.mealdeas.data.response.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealdeasApi {

    companion object {
        const val BASE_URL = "http://192.168.0.214:3000/api/"
    }

    @GET("recipes")
    suspend fun getRecipes(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): RecipesResponse
}