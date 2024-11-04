package com.dicoding.asclepius.domain.repository

import com.dicoding.asclepius.data.remote.model.NewsResponseDto

interface RemoteRepository {

    suspend fun getArticle(q: String, category: String, language: String, apiKey: String): NewsResponseDto

}