package com.dicoding.asclepius.data.remote.repository

import com.dicoding.asclepius.data.remote.ApiService
import com.dicoding.asclepius.data.remote.model.NewsResponseDto
import com.dicoding.asclepius.domain.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl(
    private val apiService: ApiService
) : RemoteRepository {
    override suspend fun getArticle(q: String, category: String, language: String, apiKey: String): NewsResponseDto {
        return withContext(Dispatchers.IO) {
            apiService.getArticle(q, category, language, apiKey)
        }
    }
}