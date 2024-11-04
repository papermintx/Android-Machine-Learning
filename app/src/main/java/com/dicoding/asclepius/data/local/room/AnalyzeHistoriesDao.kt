package com.dicoding.asclepius.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.AnalyzeHistoriesEntity


@Dao
interface AnalyzeHistoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalyzeHistory(history: AnalyzeHistoriesEntity)

    @Query("SELECT * FROM history ORDER BY date DESC")
    fun getAllAnalyzeHistories(): List<AnalyzeHistoriesEntity>

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun deleteHistoryById(id: Int)
}