package com.dicoding.asclepius.data.remote.model

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class NewsResponseDto(

	@Json(name="totalResults")
	val totalResults: Int? = null,

	@Json(name="articles")
	val articles: List<ArticlesItemDto?>? = null,

	@Json(name="status")
	val status: String? = null
)

@JsonClass(generateAdapter = true)
data class SourceDto(

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: String? = null
)

@JsonClass(generateAdapter = true)
data class ArticlesItemDto(

	@Json(name="publishedAt")
	val publishedAt: String? = null,

	@Json(name="author")
	val author: String? = null,

	@Json(name="urlToImage")
	val urlToImage: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="source")
	val source: SourceDto? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="url")
	val url: String? = null,

	@Json(name="content")
	val content: String? = null
)
