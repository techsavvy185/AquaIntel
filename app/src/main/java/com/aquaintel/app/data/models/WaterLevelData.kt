package com.aquaintel.app.data.models

data class WaterLevelData(
    val timestamp: Long,
    val waterLevel: Double, // meters below ground level
    val rechargeRate: Double // m/day
)
