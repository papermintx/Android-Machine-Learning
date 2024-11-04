package com.dicoding.asclepius.view.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.AnalyzeHistoriesEntity
import com.dicoding.asclepius.domain.usecase.UseCase
import com.dicoding.asclepius.helper.ImageClassifierHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: UseCase,
    private val imageClassifierHelper: ImageClassifierHelper
) : ViewModel(), ImageClassifierHelper.ClassifierListener {

    private val _classificationResult = MutableLiveData<Pair<String, String>?>()
    val classificationResult: LiveData<Pair<String, String>?> = _classificationResult

    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> get() = _currentImageUri

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        imageClassifierHelper.classifierListener = this
    }

    fun analyzeImage(imageUri: Uri) {
        imageClassifierHelper.classifyImage(imageUri)
    }

    /**
     * Save classification results to the database
     */
    fun saveAnalyzeHistory(imageUri: Uri, result: String, score: String) {
        val history = AnalyzeHistoriesEntity(
            image = imageUri.toString(),
            result = result,
            date = getCurrentDateTime(),
            score = score
        )
        viewModelScope.launch {
            try {
                useCase.insertAnalyzeHistory(history)
            } catch (e: Exception) {
                setError("Error saving history: ${e.message}")
            }
        }
    }

    /**
     * Set current image URI
     */
    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    /**
     * Set error message
     */
    private fun setError(errorMessage: String) {
        _error.value = errorMessage
        _classificationResult.value = null
    }

    /**
     * Helper function to get the current date and time
     */
    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    override fun onError(error: String) {
        setError("Error: $error")
    }

    override fun onResults(
        results: List<Classifications>?,
        inferenceTime: Long
    ) {
        results?.let {
            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                val sortedCategories = it[0].categories.sortedByDescending { it?.score }
                val predict = sortedCategories[0].label
                val score = NumberFormat.getPercentInstance().format(sortedCategories[0].score)
                _classificationResult.value = predict to score
                _error.value = null
            } else {
                setError("No result found.")
            }
        } ?: setError("Classification results are null.")
    }

    fun setResultsNull() {
        _classificationResult.value = null
    }

}
