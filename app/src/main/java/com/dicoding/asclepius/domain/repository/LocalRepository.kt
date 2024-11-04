package com.dicoding.asclepius.domain.repository

import com.dicoding.asclepius.data.local.entity.AnalyzeHistoriesEntity
import com.dicoding.asclepius.domain.model.AnalyzeHistories

interface LocalRepository {
    suspend fun insertAnalyzeHistory(history: AnalyzeHistoriesEntity)

    suspend fun getAnalyzeHistories(): List<AnalyzeHistories>

    suspend fun deleteHistoryById(id: Int)

}