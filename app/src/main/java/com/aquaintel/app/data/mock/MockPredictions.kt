package com.aquaintel.app.data.mock

import com.aquaintel.app.data.models.PredictionData
import java.util.Calendar
import kotlin.random.Random

object MockPredictions {

    /**
     * Generate mock prediction data for the next N days
     */
    fun getPredictionData(stationId: String, daysAhead: Int = 30): List<PredictionData> {
        val predictions = mutableListOf<PredictionData>()
        val calendar = Calendar.getInstance()

        // Get current water level from the station
        val currentStation = MockStations.getStationById(stationId)
        val baseLevel = currentStation?.currentWaterLevel ?: 15.0

        for (i in 1..daysAhead) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)

            // Simulate trend based on season
            val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
            val trend = when {
                dayOfYear in 90..180 -> 0.05 // Increasing (depleting) in summer
                dayOfYear in 180..270 -> -0.08 // Decreasing (recharging) during monsoon
                else -> 0.02
            }

            val predictedLevel = baseLevel + (trend * i) + Random.nextDouble(-0.5, 0.5)
            val confidence = Random.nextDouble(0.75, 0.95) // Higher confidence for near future

            predictions.add(
                PredictionData(
                    date = calendar.timeInMillis,
                    predictedWaterLevel = predictedLevel.coerceAtLeast(1.0),
                    confidence = confidence
                )
            )
        }

        return predictions
    }

    /**
     * Get seasonal forecast summary
     */
    fun getSeasonalForecast(stationId: String): Map<String, String> {
        return mapOf(
            "Summer (Apr-Jun)" to "Expected depletion of 2.5-4.0 meters",
            "Monsoon (Jul-Sep)" to "Expected recharge of 4.5-6.0 meters",
            "Post-Monsoon (Oct-Nov)" to "Stable levels with minor fluctuations",
            "Winter (Dec-Feb)" to "Gradual depletion of 0.5-1.5 meters"
        )
    }
}
