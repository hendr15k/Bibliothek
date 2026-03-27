package com.example.videodownloader.data.repository

import com.example.videodownloader.domain.model.VideoInfo
import com.example.videodownloader.domain.repository.VideoRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor() : VideoRepository {
    override suspend fun extractVideoInfo(url: String): Result<VideoInfo> {
        return try {
            // Simulate network delay for extraction
            delay(1500)

            if (url.isBlank()) {
                throw Exception("URL cannot be empty")
            }

            // In a real app, this would make an API call or use yt-dlp to extract info
            val mockInfo = VideoInfo(
                title = "Example Video Title extracted from ${url.take(15)}...",
                sourceUrl = url,
                thumbnailUrl = "https://picsum.photos/seed/${url.hashCode()}/400/225",
                availableQualities = listOf("1080p", "720p", "480p", "Audio Only")
            )
            Result.success(mockInfo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
