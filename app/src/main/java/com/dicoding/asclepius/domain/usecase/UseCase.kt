package com.dicoding.asclepius.domain.usecase

data class UseCase(
    val getAllAnalyzeHistory: GetAllAnalyzeHistory,
    val getNewsUseCase: GetNewsUseCase,
    val insertAnalyzeHistory: InsertAnalyzeHistory,
    val deleteHistoryById: DeleteHistoryById
)