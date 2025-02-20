package com.invo.nflphotosharingdomain.usecase

import com.invo.nflphotosharingdata.repository.UserRepository
import javax.inject.Inject

class SetUserSessionUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(isLoggedIn: Boolean) = repository.setUserLoggedIn(isLoggedIn)
}