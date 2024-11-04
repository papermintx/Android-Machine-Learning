package com.dicoding.asclepius.domain.usecase

import com.dicoding.asclepius.domain.model.AnalyzeHistories
import com.dicoding.asclepius.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllAnalyzeHistory @Inject constructor(
    private val localRepository: LocalRepository
){
    operator fun invoke(): Flow<List<AnalyzeHistories>> = flow {
        val histories = localRepository.getAnalyzeHistories()
        emit(histories)
    }.flowOn(Dispatchers.IO)
}