package com.aquaintel.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AquaIntelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any required services
    }
}
