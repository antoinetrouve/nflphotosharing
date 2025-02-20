package com.invo.nflphotosharingdomain.usecase

import com.invo.nflphotosharingdata.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() {
        userRepository.setUserLoggedIn(false)
    }
}