package com.invo.nflphotosharingdomain.usecase

import com.invo.nflphotosharingdata.exception.NFLPhotoException.AuthException.InvalidPasswordException
import com.invo.nflphotosharingdata.exception.NFLPhotoException.AuthException.InvalidUsernameException
import com.invo.nflphotosharingdata.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): LoginResult {
        val result = userRepository.login(username, password)
        return if (result.isSuccess) {
            LoginResult.Success
        } else {
            when (result.exceptionOrNull()) {
                is InvalidUsernameException -> LoginResult.InvalidUsername
                is InvalidPasswordException -> LoginResult.InvalidPassword
                else -> LoginResult.GenericError
            }
        }
    }
}

sealed class LoginResult {
    data object Success : LoginResult()
    data object InvalidUsername : LoginResult()
    data object InvalidPassword : LoginResult()
    data object GenericError : LoginResult()
}