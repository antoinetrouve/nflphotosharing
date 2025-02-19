package com.invo.nflphotosharingdata.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun setUserLoggedIn(isLoggedIn: Boolean)
}