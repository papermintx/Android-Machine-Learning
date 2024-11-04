package com.dicoding.asclepius.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "history")
@Parcelize
data class AnalyzeHistoriesEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "result") val result: String? = null,
    @ColumnInfo(name = "date") val date: String? = null,
    @ColumnInfo(name = "score") val score: String? = null
): Parcelable