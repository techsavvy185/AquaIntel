package com.aquaintel.app.data.mock

import com.aquaintel.app.data.models.RainfallData
import java.util.Calendar
import kotlin.random.Random

object MockRainfallData {

    /**
     * Generate mock rainfall data for a district
     */
    fun getRainfallData(district: String, daysBack: Int = 365): List<RainfallData> {
        val data = mutableListOf<RainfallData>()
        val calendar = Calendar.getInstance()

        for (i in 0 until daysBack) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

            // Monsoon season: June-September (days 150-273)
            val rainfall = when {
                dayOfYear in 150..273 -> Random.nextDouble(0.0, 150.0) // Monsoon
                dayOfYear in 274..365 || dayOfYear in 1..59 -> Random.nextDouble(0.0, 20.0) // Post-monsoon/winter
                else -> Random.nextDouble(0.0, 5.0) // Summer - very low
            }

            data.add(
                RainfallData(
                    date = calendar.timeInMillis,
                    rainfall = rainfall
                )
            )
        }

        return data.reversed()
    }

    /**
     * Get monthly aggregated rainfall
     */
    fun getMonthlyRainfall(district: String, monthsBack: Int = 12): Map<String, Double> {
        val monthlyData = mutableMapOf<String, Double>()
        val calendar = Calendar.getInstance()

        for (i in 0 until monthsBack) {
            calendar.add(Calendar.MONTH, -1)
            val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, java.util.Locale.ENGLISH) ?: ""
            val monthRainfall = Random.nextDouble(20.0, 300.0)
            monthlyData[monthName] = monthRainfall
        }

        return monthlyData
    }
}
