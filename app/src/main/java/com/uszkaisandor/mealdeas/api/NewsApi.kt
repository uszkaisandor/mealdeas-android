package com.uszkaisandor.mealdeas.api

import com.uszkaisandor.mealdeas.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val ACCESS_KEY = BuildConfig.NEWS_API_ACCESS_KEY
    }

    //@Headers("Non-Authenticated: true")
    @GET("top-headlines?country=hu&pageSize=100")
    suspend fun getBreakingNews(): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse
}