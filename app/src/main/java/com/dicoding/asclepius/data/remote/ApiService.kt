package com.dicoding.asclepius.data.remote

import com.dicoding.asclepius.data.remote.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getArticle(
        @Query("q") q: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): NewsResponseDto
}