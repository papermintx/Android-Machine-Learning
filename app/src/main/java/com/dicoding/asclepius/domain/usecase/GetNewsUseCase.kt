package com.dicoding.asclepius.domain.usecase

import com.dicoding.asclepius.data.remote.model.NewsResponseDto
import com.dicoding.asclepius.domain.repository.RemoteRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {

    suspend operator fun invoke(q: String, category: String, language: String, apiKey: String): NewsResponseDto {
        val response = remoteRepository.getArticle(q, category, language, apiKey)
        return response
    }
}