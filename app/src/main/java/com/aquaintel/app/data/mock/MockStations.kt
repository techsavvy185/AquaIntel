package com.aquaintel.app.data.mock

import com.aquaintel.app.data.models.AquiferStress
import com.aquaintel.app.data.models.DWLRStation
import com.aquaintel.app.data.models.StationStatus

object MockStations {

    val stations = listOf(
        DWLRStation(
            id = "DWLR_MH_001",
            name = "Nagpur Central DWLR",
            latitude = 21.1458,
            longitude = 79.0882,
            state = "Maharashtra",
            district = "Nagpur",
            aquiferType = "Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 7.2,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_KA_002",
            name = "Bangalore South DWLR",
            latitude = 12.9716,
            longitude = 77.5946,
            state = "Karnataka",
            district = "Bangalore",
            aquiferType = "Hard Rock",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 15.8,
            aquiferStress = AquiferStress.MEDIUM
        ),
        DWLRStation(
            id = "DWLR_RJ_003",
            name = "Jaipur North DWLR",
            latitude = 26.9124,
            longitude = 75.7873,
            state = "Rajasthan",
            district = "Jaipur",
            aquiferType = "Sandstone",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 28.5,
            aquiferStress = AquiferStress.HIGH
        ),
        DWLRStation(
            id = "DWLR_UP_004",
            name = "Lucknow Central DWLR",
            latitude = 26.8467,
            longitude = 80.9462,
            state = "Uttar Pradesh",
            district = "Lucknow",
            aquiferType = "Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 12.3,
            aquiferStress = AquiferStress.MEDIUM
        ),
        DWLRStation(
            id = "DWLR_GJ_005",
            name = "Ahmedabad West DWLR",
            latitude = 23.0225,
            longitude = 72.5714,
            state = "Gujarat",
            district = "Ahmedabad",
            aquiferType = "Basaltic",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 35.7,
            aquiferStress = AquiferStress.CRITICAL
        ),
        DWLRStation(
            id = "DWLR_TN_006",
            name = "Chennai East DWLR",
            latitude = 13.0827,
            longitude = 80.2707,
            state = "Tamil Nadu",
            district = "Chennai",
            aquiferType = "Coastal Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 8.9,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_DL_007",
            name = "Delhi Central DWLR",
            latitude = 28.7041,
            longitude = 77.1025,
            state = "Delhi",
            district = "New Delhi",
            aquiferType = "Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 18.2,
            aquiferStress = AquiferStress.HIGH
        ),
        DWLRStation(
            id = "DWLR_PB_008",
            name = "Chandigarh DWLR",
            latitude = 30.7333,
            longitude = 76.7794,
            state = "Punjab",
            district = "Chandigarh",
            aquiferType = "Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 9.5,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_WB_009",
            name = "Kolkata North DWLR",
            latitude = 22.5726,
            longitude = 88.3639,
            state = "West Bengal",
            district = "Kolkata",
            aquiferType = "Deltaic",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 5.2,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_TG_010",
            name = "Hyderabad Central DWLR",
            latitude = 17.3850,
            longitude = 78.4867,
            state = "Telangana",
            district = "Hyderabad",
            aquiferType = "Granite-Gneiss",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 14.6,
            aquiferStress = AquiferStress.MEDIUM
        ),
        // Adding more stations for better demonstration
        DWLRStation(
            id = "DWLR_MP_011",
            name = "Bhopal DWLR",
            latitude = 23.2599,
            longitude = 77.4126,
            state = "Madhya Pradesh",
            district = "Bhopal",
            aquiferType = "Basaltic",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 19.8,
            aquiferStress = AquiferStress.MEDIUM
        ),
        DWLRStation(
            id = "DWLR_BR_012",
            name = "Patna DWLR",
            latitude = 25.5941,
            longitude = 85.1376,
            state = "Bihar",
            district = "Patna",
            aquiferType = "Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 6.8,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_OD_013",
            name = "Bhubaneswar DWLR",
            latitude = 20.2961,
            longitude = 85.8245,
            state = "Odisha",
            district = "Bhubaneswar",
            aquiferType = "Lateritic",
            status = StationStatus.MAINTENANCE,
            currentWaterLevel = 11.4,
            aquiferStress = AquiferStress.MEDIUM
        ),
        DWLRStation(
            id = "DWLR_AS_014",
            name = "Guwahati DWLR",
            latitude = 26.1445,
            longitude = 91.7362,
            state = "Assam",
            district = "Guwahati",
            aquiferType = "Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 4.3,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_KL_015",
            name = "Thiruvananthapuram DWLR",
            latitude = 8.5241,
            longitude = 76.9366,
            state = "Kerala",
            district = "Thiruvananthapuram",
            aquiferType = "Lateritic",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 3.9,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_HR_016",
            name = "Gurugram DWLR",
            latitude = 28.4595,
            longitude = 77.0266,
            state = "Haryana",
            district = "Gurugram",
            aquiferType = "Alluvial",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 22.7,
            aquiferStress = AquiferStress.HIGH
        ),
        DWLRStation(
            id = "DWLR_JH_017",
            name = "Ranchi DWLR",
            latitude = 23.3441,
            longitude = 85.3096,
            state = "Jharkhand",
            district = "Ranchi",
            aquiferType = "Hard Rock",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 13.2,
            aquiferStress = AquiferStress.MEDIUM
        ),
        DWLRStation(
            id = "DWLR_CT_018",
            name = "Raipur DWLR",
            latitude = 21.2514,
            longitude = 81.6296,
            state = "Chhattisgarh",
            district = "Raipur",
            aquiferType = "Granite",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 16.5,
            aquiferStress = AquiferStress.MEDIUM
        ),
        DWLRStation(
            id = "DWLR_UK_019",
            name = "Dehradun DWLR",
            latitude = 30.3165,
            longitude = 78.0322,
            state = "Uttarakhand",
            district = "Dehradun",
            aquiferType = "Shale-Limestone",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 10.8,
            aquiferStress = AquiferStress.LOW
        ),
        DWLRStation(
            id = "DWLR_HP_020",
            name = "Shimla DWLR",
            latitude = 31.1048,
            longitude = 77.1734,
            state = "Himachal Pradesh",
            district = "Shimla",
            aquiferType = "Fractured Rock",
            status = StationStatus.ACTIVE,
            currentWaterLevel = 7.6,
            aquiferStress = AquiferStress.LOW
        )
    )

    fun getStationById(id: String): DWLRStation? {
        return stations.find { it.id == id }
    }

    fun getStationsByState(state: String): List<DWLRStation> {
        return stations.filter { it.state == state }
    }

    fun getStationsByDistrict(district: String): List<DWLRStation> {
        return stations.filter { it.district == district }
    }

    fun getStationsByStatus(status: StationStatus): List<DWLRStation> {
        return stations.filter { it.status == status }
    }

    fun getStationsByAquiferStress(stress: AquiferStress): List<DWLRStation> {
        return stations.filter { it.aquiferStress == stress }
    }
}
