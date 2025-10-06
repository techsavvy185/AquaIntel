package com.aquaintel.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.aquaintel.app.ui.theme.ForestGreen
import com.aquaintel.app.ui.theme.LocalAppColors
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GaugeIndicator(
    value: Float,
    maxValue: Float = 100f,
    label: String,
    modifier: Modifier = Modifier
) {
    val percentage = (value / maxValue).coerceIn(0f, 1f)
    val sweepAngle = 270f * percentage

    val gaugeColors = listOf(
        ForestGreen,
        Color(0xFFFFEB3B),
        Color(0xFFFF9800),
        Color(0xFFD32F2F)
    )

    // Capture colors outside Canvas
    val backgroundArcColor = LocalAppColors.current.divider
    val foregroundArcBrush = Brush.sweepGradient(gaugeColors)

    Box(
        modifier = modifier.size(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(150.dp)) {
            val strokeWidth = 20f
            val radius = (size.minDimension - strokeWidth) / 2

            // Background arc
            drawArc(
                color = backgroundArcColor,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(
                    (size.width - radius * 2) / 2,
                    (size.height - radius * 2) / 2
                )
            )

            // Progress arc with gradient
            drawArc(
                brush = foregroundArcBrush,
                startAngle = 135f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(
                    (size.width - radius * 2) / 2,
                    (size.height - radius * 2) / 2
                )
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value.toInt().toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = LocalAppColors.current.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = LocalAppColors.current.editText
            )
        }
    }
}
