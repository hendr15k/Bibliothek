package com.example.videodownloader.domain.repository

import com.example.videodownloader.domain.model.DownloadItem
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {
    fun getAllDownloads(): Flow<List<DownloadItem>>
    suspend fun startDownload(id: String, url: String, title: String, quality: String)
    suspend fun updateProgress(id: String, progress: Int)
    suspend fun updateStatus(id: String, status: com.example.videodownloader.domain.model.DownloadStatus)
}
