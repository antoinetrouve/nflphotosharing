package com.invo.nflphotosharingdata.di

import android.content.Context
import com.invo.nflphotosharingdata.local.UserPreferenceDataStore
import com.invo.nflphotosharingdata.repository.PhotoRepository
import com.invo.nflphotosharingdata.repository.PhotoRepositoryImpl
import com.invo.nflphotosharingdata.repository.UserRepository
import com.invo.nflphotosharingdata.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providePhotoRepository(): PhotoRepository = PhotoRepositoryImpl()

    @Singleton
    @Provides
    fun provideUserRepository(userPreferenceDataStore: UserPreferenceDataStore): UserRepository {
        return UserRepositoryImpl(userPreferenceDataStore)
    }
}