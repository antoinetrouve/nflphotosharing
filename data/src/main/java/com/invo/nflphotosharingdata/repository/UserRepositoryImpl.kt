package com.invo.nflphotosharingdata.repository

import com.invo.nflphotosharingdata.local.UserPreferenceDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userPreferenceDataStore: UserPreferenceDataStore
) : UserRepository {
    override fun isUserLoggedIn(): Flow<Boolean> = userPreferenceDataStore.isUserLoggedIn

    override suspend fun setUserLoggedIn(isLoggedIn: Boolean) = userPreferenceDataStore.setUserLoggedIn(isLoggedIn)
}