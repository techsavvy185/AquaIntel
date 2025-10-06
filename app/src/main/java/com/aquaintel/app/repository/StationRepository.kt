package com.aquaintel.app.repository

import com.aquaintel.app.data.mock.MockStations
import com.aquaintel.app.data.mock.MockTimeSeriesData
import com.aquaintel.app.data.models.DWLRStation
import com.aquaintel.app.data.models.WaterLevelData
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationRepository @Inject constructor() {

    suspend fun getAllStations(): List<DWLRStation> {
        delay(500) // Simulate network delay
        return MockStations.stations
    }

    suspend fun getStationById(id: String): DWLRStation? {
        delay(300)
        return MockStations.getStationById(id)
    }

    suspend fun getStationsByState(state: String): List<DWLRStation> {
        delay(400)
        return MockStations.getStationsByState(state)
    }

    suspend fun getWaterLevelData(stationId: String, days: Int): List<WaterLevelData> {
        delay(600)
        return MockTimeSeriesData.getTimeSeriesData(stationId, days)
    }

    suspend fun searchStations(query: String): List<DWLRStation> {
        delay(300)
        return MockStations.stations.filter { station ->
            station.name.contains(query, ignoreCase = true) ||
                    station.district.contains(query, ignoreCase = true) ||
                    station.state.contains(query, ignoreCase = true)
        }
    }
}
