package com.invo.nflphotosharingdata.repository

import android.net.Uri
import com.invo.nflphotosharingdata.database.UserMemoryDataStore
import com.invo.nflphotosharingdata.model.Memory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

class MemoryRepositoryImpl @Inject constructor(
    private val userMemoryDataStore: UserMemoryDataStore
) : MemoryRepository {
    override suspend fun getDemoMemories(): Result<List<Memory>> {
        delay(1000) // Simulate network delay
        val imageNames = (1..10).map { "demo$it" }
        val users = listOf("Tom", "Lisa", "Jake", "Emma", "Noah", "Ava", "Mia", "Sophia", "Liam", "Olivia")

        val memories = imageNames.mapIndexed { index, imageName ->
            Memory(
                id = index,
                imageName = imageName,
                userName = users[index],
                timestamp = System.currentTimeMillis()
            )
        }.shuffled(Random(System.currentTimeMillis()))

        return Result.success(memories)
    }

    override suspend fun getMemories(): Flow<List<Uri>> {
        return userMemoryDataStore.getPhotoUris()
            .map { uriStrings ->
                uriStrings.map { Uri.parse(it) }
            }
    }

    override suspend fun saveMemory(uri: Uri): Result<Unit> {
        return try {
            userMemoryDataStore.savePhotoUri(uri.toString())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}