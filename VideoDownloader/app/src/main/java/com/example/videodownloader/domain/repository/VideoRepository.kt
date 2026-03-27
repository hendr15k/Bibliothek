package com.example.videodownloader.domain.repository

import com.example.videodownloader.domain.model.VideoInfo

interface VideoRepository {
    suspend fun extractVideoInfo(url: String): Result<VideoInfo>
}
