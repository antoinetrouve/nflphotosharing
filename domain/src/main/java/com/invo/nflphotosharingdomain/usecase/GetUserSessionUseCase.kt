package com.invo.nflphotosharingdomain.usecase

import com.invo.nflphotosharingdata.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.lastOrNull
import javax.inject.Inject

class GetUserSessionUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isUserLoggedIn()
}