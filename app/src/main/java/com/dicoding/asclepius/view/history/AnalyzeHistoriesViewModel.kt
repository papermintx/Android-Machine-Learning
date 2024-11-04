package com.dicoding.asclepius.view.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.domain.model.AnalyzeHistories
import com.dicoding.asclepius.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyzeHistoriesViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _analyzeHistories = MutableStateFlow<List<AnalyzeHistories>>(emptyList())
    val analyzeHistories = _analyzeHistories.asStateFlow()

    fun getAllAnalyzeHistory() {
        viewModelScope.launch {
            try {
                useCase.getAllAnalyzeHistory()
                    .collect { histories ->
                        _analyzeHistories.value = histories
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }

    fun deleteById(id: Int?) {
        viewModelScope.launch {
            if (id != null) {
                try {
                    useCase.deleteHistoryById(id)
                    getAllAnalyzeHistory()
                } catch (e: Exception) {
                    Log.e(TAG, "Error: ${e.message}")
                }
            }
        }
    }


    companion object{
        const val TAG = "AnalyzeHistoriesViewModel"
    }
}

