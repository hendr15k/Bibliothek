package com.example.videodownloader.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videodownloader.domain.model.DownloadItem
import com.example.videodownloader.domain.model.DownloadStatus
import com.example.videodownloader.domain.model.VideoInfo
import com.example.videodownloader.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val downloads by viewModel.downloads.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video Downloader") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            InputSection(
                url = uiState.urlInput,
                onUrlChange = viewModel::onUrlChange,
                onExtractClick = viewModel::extractVideoInfo,
                isLoading = uiState.isLoading
            )

            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            uiState.videoInfo?.let { info ->
                Spacer(modifier = Modifier.height(16.dp))
                VideoInfoCard(
                    videoInfo = info,
                    selectedQuality = uiState.selectedQuality,
                    onQualitySelected = viewModel::onQualitySelect,
                    onDownloadClick = viewModel::startDownload
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Downloads",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            DownloadsList(downloads = downloads)
        }
    }
}

@Composable
fun InputSection(
    url: String,
    onUrlChange: (String) -> Unit,
    onExtractClick: () -> Unit,
    isLoading: Boolean
) {
    val clipboardManager = LocalClipboardManager.current

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            label = { Text("Paste Video URL") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    clipboardManager.getText()?.let {
                        onUrlChange(it.text)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ContentPaste,
                        contentDescription = "Paste from clipboard"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onExtractClick,
            modifier = Modifier.align(Alignment.End),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Extract Info")
            }
        }
    }
}

@Composable
fun VideoInfoCard(
    videoInfo: VideoInfo,
    selectedQuality: String,
    onQualitySelected: (String) -> Unit,
    onDownloadClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = videoInfo.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Select Quality:")
            videoInfo.availableQualities.forEach { quality ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = quality == selectedQuality,
                        onClick = { onQualitySelected(quality) }
                    )
                    Text(text = quality)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDownloadClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Download")
            }
        }
    }
}

@Composable
fun DownloadsList(downloads: List<DownloadItem>) {
    LazyColumn {
        items(downloads) { item ->
            DownloadItemRow(item)
            HorizontalDivider()
        }
    }
}

@Composable
fun DownloadItemRow(item: DownloadItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${item.quality} - ${item.status}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (item.status == DownloadStatus.DOWNLOADING) {
                Text(
                    text = "${item.progress}%",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        if (item.status == DownloadStatus.DOWNLOADING) {
            LinearProgressIndicator(
                progress = item.progress / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}
