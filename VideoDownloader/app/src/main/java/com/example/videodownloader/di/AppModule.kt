package com.example.videodownloader.di

import android.content.Context
import androidx.room.Room
import com.example.videodownloader.data.local.AppDatabase
import com.example.videodownloader.data.local.DownloadDao
import com.example.videodownloader.data.repository.DownloadRepositoryImpl
import com.example.videodownloader.data.repository.VideoRepositoryImpl
import com.example.videodownloader.domain.repository.DownloadRepository
import com.example.videodownloader.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "video_downloader_db"
        ).build()
    }

    @Provides
    fun provideDownloadDao(database: AppDatabase): DownloadDao {
        return database.downloadDao()
    }

    @Provides
    @Singleton
    fun provideVideoRepository(): VideoRepository {
        return VideoRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideDownloadRepository(dao: DownloadDao): DownloadRepository {
        return DownloadRepositoryImpl(dao)
    }
}
