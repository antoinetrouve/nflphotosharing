package com.invo.nflphotosharingdata.di

import com.invo.nflphotosharingdata.repository.PhotoRepository
import com.invo.nflphotosharingdata.repository.PhotoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providePhotoRepository(): PhotoRepository = PhotoRepositoryImpl()
}