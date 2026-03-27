package com.example.videodownloader.domain.model

data class VideoInfo(
    val title: String,
    val sourceUrl: String,
    val thumbnailUrl: String,
    val availableQualities: List<String>
)
