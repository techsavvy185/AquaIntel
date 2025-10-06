package com.aquaintel.app.data.models

data class PredictionData(
    val date: Long,
    val predictedWaterLevel: Double,
    val confidence: Double // 0.0 to 1.0
)
