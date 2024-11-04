package com.dicoding.asclepius.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.mapper.toArticleItem
import com.dicoding.asclepius.domain.model.ArticlesItem
import com.dicoding.asclepius.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _news = MutableStateFlow<List<ArticlesItem?>?>(null)
    val news = _news.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        getNews("cancer", "health", "en")
    }

    fun getNews(q: String, categories: String, language: String) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null
            try {
                val response = useCase.getNewsUseCase(
                    q = q,
                    category = categories,
                    language = language,
                    apiKey = BuildConfig.API_KEY
                )

                val articles = response.articles?.filter { article ->
                    article?.url != "https://removed.com"
                }?.map { article ->
                    article?.toArticleItem()
                } ?: emptyList()

                if (articles.isEmpty()) {
                    _errorMessage.value = "No articles available."
                }

                _news.value = articles
            } catch (e: Exception) {
                _news.value = emptyList()
                _errorMessage.value = "Failed to load news: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


    fun refreshNews(q: String, categories: String, language: String) {
        getNews(q, categories, language)
    }
}
