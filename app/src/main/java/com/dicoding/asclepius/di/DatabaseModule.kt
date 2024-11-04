package com.dicoding.asclepius.di

import android.content.Context
import androidx.room.Room
import com.dicoding.asclepius.data.local.repository.LocalRepositoryImpl
import com.dicoding.asclepius.data.local.room.AnalyzeHistoriesDao
import com.dicoding.asclepius.data.local.room.AppDatabase
import com.dicoding.asclepius.domain.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAnalyzeHistoriesDatabase( @ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "analyze_histories.db"
        ).build()
    }

    @Provides
    fun provideAnalyzeHistoriesDao(appDatabase: AppDatabase): AnalyzeHistoriesDao {
        return appDatabase.analyzeHistoriesDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(analyzeHistoriesDao: AnalyzeHistoriesDao): LocalRepository {
        return LocalRepositoryImpl(analyzeHistoriesDao)
    }

}