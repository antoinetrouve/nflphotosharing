package com.invo.nflphotosharingdomain.usecase

import android.net.Uri
import com.invo.nflphotosharingdata.repository.MemoryRepository
import javax.inject.Inject

class SaveUserMemoryUseCase @Inject constructor(
    private val memoryRepository: MemoryRepository
) {
    suspend operator fun invoke(uri: Uri): Result<Unit> {
        return memoryRepository.saveMemory(uri)
    }
}