package com.dicoding.asclepius.data.mapper

import com.dicoding.asclepius.data.local.entity.AnalyzeHistoriesEntity
import com.dicoding.asclepius.domain.model.AnalyzeHistories

fun AnalyzeHistoriesEntity.toAnalyzeHistory() = AnalyzeHistories(
    id = id,
    image = image,
    result = result,
    date = date,
    score = score.toString()
)
