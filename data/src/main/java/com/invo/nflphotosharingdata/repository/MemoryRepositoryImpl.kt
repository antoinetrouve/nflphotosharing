package com.invo.nflphotosharingdata.repository

import com.invo.nflphotosharingdata.model.Memory
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class MemoryRepositoryImpl @Inject constructor() : MemoryRepository {
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
}