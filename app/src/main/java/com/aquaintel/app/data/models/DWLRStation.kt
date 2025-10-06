package com.aquaintel.app.data.models

data class DWLRStation(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val state: String,
    val district: String,
    val aquiferType: String,
    val status: StationStatus,
    val currentWaterLevel: Double, // meters below ground level
    val aquiferStress: AquiferStress
)

enum class StationStatus {
    ACTIVE, INACTIVE, MAINTENANCE
}

enum class AquiferStress {
    LOW, MEDIUM, HIGH, CRITICAL
}
