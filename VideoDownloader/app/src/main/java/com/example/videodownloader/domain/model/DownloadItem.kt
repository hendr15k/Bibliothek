package com.example.videodownloader.domain.model

data class DownloadItem(
    val id: String,
    val title: String,
    val url: String,
    val quality: String,
    val progress: Int,
    val status: DownloadStatus,
    val timestamp: Long
)

enum class DownloadStatus {
    PENDING, DOWNLOADING, COMPLETED, FAILED
}
