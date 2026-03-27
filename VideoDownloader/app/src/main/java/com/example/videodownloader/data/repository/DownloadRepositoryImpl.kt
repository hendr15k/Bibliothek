package com.example.videodownloader.data.repository

import com.example.videodownloader.data.local.DownloadDao
import com.example.videodownloader.data.local.DownloadEntity
import com.example.videodownloader.domain.model.DownloadItem
import com.example.videodownloader.domain.model.DownloadStatus
import com.example.videodownloader.domain.repository.DownloadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
    private val dao: DownloadDao
) : DownloadRepository {

    override fun getAllDownloads(): Flow<List<DownloadItem>> {
        return dao.getAllDownloads().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun startDownload(id: String, url: String, title: String, quality: String) {
        val entity = DownloadEntity(
            id = id,
            title = title,
            url = url,
            quality = quality,
            progress = 0,
            status = DownloadStatus.DOWNLOADING.name,
            timestamp = System.currentTimeMillis()
        )
        dao.insertDownload(entity)
    }

    override suspend fun updateProgress(id: String, progress: Int) {
        dao.updateProgress(id, progress)
    }

    override suspend fun updateStatus(id: String, status: DownloadStatus) {
        dao.updateStatus(id, status.name)
    }
}

fun DownloadEntity.toDomain() = DownloadItem(
    id = id,
    title = title,
    url = url,
    quality = quality,
    progress = progress,
    status = try {
        DownloadStatus.valueOf(status)
    } catch (e: IllegalArgumentException) {
        DownloadStatus.FAILED
    },
    timestamp = timestamp
)
