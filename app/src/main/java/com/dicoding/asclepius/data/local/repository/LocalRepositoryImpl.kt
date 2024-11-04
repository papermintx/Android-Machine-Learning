package com.dicoding.asclepius.data.local.repository

import com.dicoding.asclepius.data.local.entity.AnalyzeHistoriesEntity
import com.dicoding.asclepius.data.local.room.AnalyzeHistoriesDao
import com.dicoding.asclepius.data.mapper.toAnalyzeHistory
import com.dicoding.asclepius.domain.model.AnalyzeHistories
import com.dicoding.asclepius.domain.repository.LocalRepository

class LocalRepositoryImpl(
    private val daoHistory: AnalyzeHistoriesDao,
): LocalRepository{

    override suspend fun insertAnalyzeHistory(history: AnalyzeHistoriesEntity) {
        daoHistory.insertAnalyzeHistory(history)
    }

    override suspend fun getAnalyzeHistories(): List<AnalyzeHistories> {
        val data = daoHistory.getAllAnalyzeHistories()
        val convert = data.map {
            it.toAnalyzeHistory()
        }

        return convert
    }

    override suspend fun deleteHistoryById(id: Int) {
        daoHistory.deleteHistoryById(id)
    }


    companion object{
        const val TAG = "LocalRepositoryImpl"
    }
}