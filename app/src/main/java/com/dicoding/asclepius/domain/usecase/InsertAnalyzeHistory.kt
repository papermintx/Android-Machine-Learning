package com.dicoding.asclepius.domain.usecase

import com.dicoding.asclepius.data.local.entity.AnalyzeHistoriesEntity
import com.dicoding.asclepius.domain.repository.LocalRepository
import javax.inject.Inject

class InsertAnalyzeHistory @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(analyzeHistories: AnalyzeHistoriesEntity) {
        localRepository.insertAnalyzeHistory(analyzeHistories)
    }
}