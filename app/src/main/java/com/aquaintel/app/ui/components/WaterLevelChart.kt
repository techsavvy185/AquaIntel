package com.aquaintel.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aquaintel.app.data.models.WaterLevelData
import com.aquaintel.app.ui.theme.LocalAppColors
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.CartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.shader.DynamicShader

@Composable
fun WaterLevelChart(
    data: List<WaterLevelData>,
    modifier: Modifier = Modifier
) {
    // 1. Remember the ModelProducer instance.
    val modelProducer = remember { CartesianChartModelProducer.build() }

    // 2. Use LaunchedEffect to update the data when the list changes.
    LaunchedEffect(data) {
        modelProducer.runTransaction {
            lineSeries {
                series(y = data.map { it.waterLevel })
            }
        }
    }

    // 3. Use the deprecated CartesianChartHost.
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lines = listOf(
                    // FIXED: Added a color shader to the line specification.
                    rememberLineSpec(
                        shader = DynamicShader.color(LocalAppColors.current.primary)
                    )
                )
            ),
            startAxis = rememberStartAxis(
                label = rememberAxisLabelComponent()
            ),
            bottomAxis = rememberBottomAxis(
                label = rememberAxisLabelComponent()
            )
        ),
        modelProducer = modelProducer,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}