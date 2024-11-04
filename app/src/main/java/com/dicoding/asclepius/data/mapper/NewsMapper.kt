package com.dicoding.asclepius.data.mapper

import com.dicoding.asclepius.data.remote.model.ArticlesItemDto
import com.dicoding.asclepius.data.remote.model.SourceDto
import com.dicoding.asclepius.domain.model.ArticlesItem
import com.dicoding.asclepius.domain.model.Source

fun ArticlesItemDto.toArticleItem() = ArticlesItem(
    publishedAt = publishedAt,
    author = author,
    urlToImage = urlToImage,
    description = description,
    source = source?.toSource(),
    title = title,
    url = url,
    content = content
)

fun SourceDto.toSource() =Source(
    name = name,
    id = id
)

