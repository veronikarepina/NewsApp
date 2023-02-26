package ru.veronikarepina.mynewsapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.veronikarepina.mynewsapp.model.NewsModel
import ru.veronikarepina.mynewsapp.utils.Constants.Companion.API_KEY
import ru.veronikarepina.mynewsapp.utils.Constants.Companion.CATEGORY
import ru.veronikarepina.mynewsapp.utils.Constants.Companion.COUNTRY

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getNewsApi(
        @Query("country") country: String = COUNTRY,
        @Query("category") category: String = CATEGORY,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsModel
}