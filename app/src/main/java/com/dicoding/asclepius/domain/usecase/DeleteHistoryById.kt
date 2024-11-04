package com.dicoding.asclepius.domain.usecase

import com.dicoding.asclepius.domain.repository.LocalRepository
import javax.inject.Inject

class DeleteHistoryById @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(id: Int) {
        localRepository.deleteHistoryById(id)
    }
}