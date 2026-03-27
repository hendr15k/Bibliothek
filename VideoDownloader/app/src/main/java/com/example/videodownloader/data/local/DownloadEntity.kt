package com.example.videodownloader.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.videodownloader.domain.model.DownloadStatus

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey val id: String,
    val title: String,
    val url: String,
    val quality: String,
    val progress: Int,
    val status: String,
    val timestamp: Long
)
