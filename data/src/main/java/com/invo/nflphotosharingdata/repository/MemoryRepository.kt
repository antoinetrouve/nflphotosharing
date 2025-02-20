package com.invo.nflphotosharingdata.repository

import com.invo.nflphotosharingdata.model.Memory

interface MemoryRepository {
    suspend fun getDemoMemories(): Result<List<Memory>>
}