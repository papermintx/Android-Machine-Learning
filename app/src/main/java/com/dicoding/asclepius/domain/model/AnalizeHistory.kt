package com.dicoding.asclepius.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnalyzeHistories (
    val id: Int? = null,
    val image: String? = null,
    val result: String? = null,
    val date: String? = null,
    val score: String
): Parcelable