package com.aquaintel.app.data.mock

import com.aquaintel.app.data.models.WaterLevelData
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

object MockTimeSeriesData {

    /**
     * Generates time series water level data for the past 2 years
     * with seasonal variations (lower in summer, higher post-monsoon)
     */
    fun getTimeSeriesData(stationId: String, daysBack: Int = 730): List<WaterLevelData> {
        val data = mutableListOf<WaterLevelData>()
        val calendar = Calendar.getInstance()
        val baseLevel = Random.nextDouble(5.0, 25.0) // Base water level

        for (i in 0 until daysBack) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

            // Simulate seasonal variation
            // Lower in summer (April-June: days 90-180), higher post-monsoon (July-Sept: days 180-270)
            val seasonalVariation = when {
                dayOfYear in 90..180 -> -3.0 * sin((dayOfYear - 90) * Math.PI / 90) // Summer dip
                dayOfYear in 180..270 -> 5.0 * cos((dayOfYear - 180) * Math.PI / 90) // Monsoon recharge
                else -> 0.0
            }

            val randomNoise = Random.nextDouble(-0.5, 0.5)
            val waterLevel = baseLevel + seasonalVariation + randomNoise

            // Calculate mock recharge rate
            val rechargeRate = when {
                dayOfYear in 180..270 -> Random.nextDouble(0.08, 0.15) // High during monsoon
                dayOfYear in 90..180 -> Random.nextDouble(-0.05, 0.02) // Negative/low in summer
                else -> Random.nextDouble(0.01, 0.05) // Normal
            }

            data.add(
                WaterLevelData(
                    timestamp = calendar.timeInMillis,
                    waterLevel = waterLevel.coerceAtLeast(1.0),
                    rechargeRate = rechargeRate
                )
            )
        }

        return data.reversed()
    }

    /**
     * Get recent data for the past N days
     */
    fun getRecentData(stationId: String, days: Int = 30): List<WaterLevelData> {
        return getTimeSeriesData(stationId, days)
    }

    /**
     * Get hourly data for today
     */
    fun getTodayHourlyData(stationId: String): List<WaterLevelData> {
        val data = mutableListOf<WaterLevelData>()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val baseLevel = Random.nextDouble(5.0, 25.0)

        for (hour in 0..23) {
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            val waterLevel = baseLevel + Random.nextDouble(-0.3, 0.3)
            val rechargeRate = Random.nextDouble(-0.02, 0.05)

            data.add(
                WaterLevelData(
                    timestamp = calendar.timeInMillis,
                    waterLevel = waterLevel,
                    rechargeRate = rechargeRate
                )
            )
        }

        return data
    }
}
