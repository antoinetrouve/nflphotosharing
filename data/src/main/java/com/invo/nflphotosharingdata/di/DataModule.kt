package com.invo.nflphotosharingdata.di

import com.invo.nflphotosharingdata.database.UserMemoryDataStore
import com.invo.nflphotosharingdata.database.UserPreferenceDataStore
import com.invo.nflphotosharingdata.repository.MemoryRepository
import com.invo.nflphotosharingdata.repository.MemoryRepositoryImpl
import com.invo.nflphotosharingdata.repository.UserRepository
import com.invo.nflphotosharingdata.repository.UserRepositoryImpl
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
    fun provideMemoryRepository(
        userMemoryDataStore: UserMemoryDataStore
    ): MemoryRepository = MemoryRepositoryImpl(userMemoryDataStore)

    @Singleton
    @Provides
    fun provideUserRepository(userPreferenceDataStore: UserPreferenceDataStore): UserRepository {
        return UserRepositoryImpl(userPreferenceDataStore)
    }
}