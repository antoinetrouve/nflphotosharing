package com.invo.nflphotosharingdata.repository

import com.invo.nflphotosharingdata.database.UserPreferenceDataStore
import com.invo.nflphotosharingdata.exception.NFLPhotoException
import com.invo.nflphotosharingdata.exception.NFLPhotoException.AuthException.InvalidPasswordException
import com.invo.nflphotosharingdata.exception.NFLPhotoException.AuthException.InvalidUsernameException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userPreferenceDataStore: UserPreferenceDataStore
) : UserRepository {
    private val mockUsername = "demo"
    private val mockPassword = "demo"

    override fun isUserLoggedIn(): Flow<Boolean> = userPreferenceDataStore.isUserLoggedIn

    override suspend fun setUserLoggedIn(isLoggedIn: Boolean) = userPreferenceDataStore.setUserLoggedIn(isLoggedIn)

    override suspend fun login(username: String, password: String): Result<Unit> {
        delay(1000) // Simulate network delay

        return when {
            username != mockUsername -> Result.failure(InvalidUsernameException())
            password != mockPassword -> Result.failure(InvalidPasswordException())
            else -> Result.success(Unit)
        }
    }
}