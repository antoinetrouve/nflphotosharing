package com.invo.nflphotosharingdomain.usecase

import com.invo.nflphotosharingdata.repository.MemoryRepository
import com.invo.nflphotosharingdomain.model.Memory
import javax.inject.Inject

class GetMemoriesUseCase @Inject constructor(
    private val memoryRepository: MemoryRepository
) {
    suspend operator fun invoke(): MemoriesResult {
        val result = memoryRepository.getDemoMemories()
        return if (result.isSuccess) {
            val domainMemories = result.getOrNull()?.map { dataMemory ->
                Memory(
                    id = dataMemory.id,
                    userName = dataMemory.userName,
                    timestamp = dataMemory.timestamp,
                    imageName = dataMemory.imageName
                )
            } ?: emptyList()
            MemoriesResult.Success(domainMemories)
        } else {
            MemoriesResult.Error
        }
    }
}

sealed class MemoriesResult {
    data class Success(val memories: List<Memory>) : MemoriesResult()
    data object Error : MemoriesResult()
}