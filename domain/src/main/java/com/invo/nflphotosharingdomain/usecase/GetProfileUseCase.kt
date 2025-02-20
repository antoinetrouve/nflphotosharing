package com.invo.nflphotosharingdomain.usecase

import android.net.Uri
import com.invo.nflphotosharingdata.repository.MemoryRepository
import com.invo.nflphotosharingdata.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val memoryRepository: MemoryRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): ProfileResult {
        return try {
            val username = userRepository.getUsername()
            val memories = memoryRepository.getMemories()
            ProfileResult.Success(memories.first(), username)
        } catch (e: Exception) {
            ProfileResult.Error
        }
    }
}

sealed class ProfileResult {
    data class Success(val memories: List<Uri>, val username: String) : ProfileResult()
    data object Error : ProfileResult()
}