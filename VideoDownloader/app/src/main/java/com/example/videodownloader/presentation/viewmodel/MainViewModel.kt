package com.example.videodownloader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videodownloader.domain.model.DownloadItem
import com.example.videodownloader.domain.model.DownloadStatus
import com.example.videodownloader.domain.model.VideoInfo
import com.example.videodownloader.domain.repository.DownloadRepository
import com.example.videodownloader.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val downloadRepository: DownloadRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    val downloads: StateFlow<List<DownloadItem>> = downloadRepository.getAllDownloads()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onUrlChange(newUrl: String) {
        _uiState.update { it.copy(urlInput = newUrl, error = null) }
    }

    fun extractVideoInfo() {
        val url = _uiState.value.urlInput
        if (url.isBlank()) {
            _uiState.update { it.copy(error = "Please enter a valid URL") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = videoRepository.extractVideoInfo(url)

            result.onSuccess { info ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        videoInfo = info,
                        selectedQuality = info.availableQualities.firstOrNull() ?: ""
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to extract video info"
                    )
                }
            }
        }
    }

    fun onQualitySelect(quality: String) {
        _uiState.update { it.copy(selectedQuality = quality) }
    }

    fun startDownload() {
        val state = _uiState.value
        val info = state.videoInfo ?: return
        val url = state.urlInput
        val quality = state.selectedQuality

        viewModelScope.launch {
            val downloadId = UUID.randomUUID().toString()
            downloadRepository.startDownload(downloadId, url, info.title, quality)

            // Clear current selection
            _uiState.update { it.copy(videoInfo = null, urlInput = "") }

            // Simulate download progress
            simulateDownload(downloadId)
        }
    }

    private fun simulateDownload(downloadId: String) {
        viewModelScope.launch {
            for (progress in 1..100) {
                delay(100) // Simulate time taken to download
                downloadRepository.updateProgress(downloadId, progress)
            }
            downloadRepository.updateStatus(downloadId, DownloadStatus.COMPLETED)
        }
    }
}

data class MainUiState(
    val urlInput: String = "",
    val isLoading: Boolean = false,
    val videoInfo: VideoInfo? = null,
    val selectedQuality: String = "",
    val error: String? = null
)
