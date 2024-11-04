package com.dicoding.asclepius.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.AnalyzeHistoriesEntity


@Database(entities = [AnalyzeHistoriesEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun analyzeHistoriesDao(): AnalyzeHistoriesDao
}