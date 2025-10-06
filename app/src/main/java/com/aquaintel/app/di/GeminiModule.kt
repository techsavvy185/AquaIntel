package com.aquaintel.app.di

import com.aquaintel.app.BuildConfig
import com.aquaintel.app.data.ai.GeminiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeminiModule {

    @Provides
    @Singleton
    fun provideGeminiService(): GeminiService {
        val service = GeminiService()
        service.initialize(BuildConfig.GEMINI_API_KEY)
        return service
    }
}
