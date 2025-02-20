package com.invo.nflphotosharingdata.repository

import android.net.Uri
import com.invo.nflphotosharingdata.model.Memory
import kotlinx.coroutines.flow.Flow

interface MemoryRepository {
    suspend fun getDemoMemories(): Result<List<Memory>>
    suspend fun getMemories(): Flow<List<Uri>>
    suspend fun saveMemory(uri: Uri): Result<Unit>
}