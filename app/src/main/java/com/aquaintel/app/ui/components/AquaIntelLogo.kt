package com.aquaintel.app.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aquaintel.app.R
import com.aquaintel.app.ui.theme.ClashDisplay

@Composable
fun AquaIntelLogo(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.aquaintel_logo),
                contentDescription = "AquaIntel Logo",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "AquaIntel",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = ClashDisplay
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
