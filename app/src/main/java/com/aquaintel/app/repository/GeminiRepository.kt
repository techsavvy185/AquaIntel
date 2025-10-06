package com.aquaintel.app.data.repository

import com.aquaintel.app.data.ai.GeminiService
import javax.inject.Inject

class GeminiRepository @Inject constructor(
    private val geminiService: GeminiService
) {
    suspend fun sendMessage(prompt: String): Result<String> {
        return try {
            val response = geminiService.sendMessage(prompt)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
