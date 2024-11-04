package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.domain.usecase.DeleteHistoryById
import com.dicoding.asclepius.domain.usecase.GetAllAnalyzeHistory
import com.dicoding.asclepius.domain.usecase.GetNewsUseCase
import com.dicoding.asclepius.domain.usecase.InsertAnalyzeHistory
import com.dicoding.asclepius.domain.usecase.UseCase
import com.dicoding.asclepius.helper.ImageClassifierHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUseCase(
        getAllAnalyzeHistory: GetAllAnalyzeHistory,
        getNewsUseCase: GetNewsUseCase,
        insertAnalyzeHistory: InsertAnalyzeHistory,
        deleteHistoryById: DeleteHistoryById
    ): UseCase{
       return UseCase(
            getAllAnalyzeHistory,
            getNewsUseCase,
            insertAnalyzeHistory,
            deleteHistoryById
        )
    }

    @Provides
    @Singleton
    fun provideImageClassifierHelper(
        @ApplicationContext context: Context
    ): ImageClassifierHelper {
        return ImageClassifierHelper(context = context)
    }
}