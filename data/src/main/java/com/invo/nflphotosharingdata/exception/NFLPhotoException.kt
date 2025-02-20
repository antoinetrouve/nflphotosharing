package com.invo.nflphotosharingdata.exception

sealed class NFLPhotoException: Exception() {
    sealed class AuthException: NFLPhotoException() {
        data class InvalidUsernameException(override val message: String = ""): AuthException()
        data class InvalidPasswordException(override val message: String = ""): AuthException()
    }
}