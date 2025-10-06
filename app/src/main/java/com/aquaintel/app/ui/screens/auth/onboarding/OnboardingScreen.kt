package com.aquaintel.app.ui.screens.auth.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aquaintel.app.R
import com.aquaintel.app.ui.components.PrimaryButton
import com.aquaintel.app.ui.components.SecondaryButton
import com.aquaintel.app.ui.theme.AquaIntelTheme
import com.aquaintel.app.ui.theme.ClashDisplay
import com.aquaintel.app.ui.theme.LocalAppColors
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val iconRes: Int
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "Real-Time Monitoring",
        description = "Access live groundwater data from 5,260+ DWLR stations across India",
        iconRes = R.drawable.ic_monitoring
    ),
    OnboardingPage(
        title = "Smart Analytics",
        description = "Visualize trends, analyze patterns, and predict future water levels",
        iconRes = R.drawable.ic_analytics
    ),
    OnboardingPage(
        title = "Informed Decisions",
        description = "Make data-driven decisions for sustainable groundwater management",
        iconRes = R.drawable.ic_decisions
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit = {}
) {
    val uiState by viewModel.onboardingUiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    OnboardingScreenLayout(
        currentPage = pagerState.currentPage,
        onNextClicked = {
            if (pagerState.currentPage < onboardingPages.size - 1) {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            } else {
                onNavigateToLogin()
            }
        },
        onSkipClicked = onNavigateToLogin,
        pagerContent = {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                OnboardingPageContent(page = onboardingPages[page])
            }
        }
    )
}

@Composable
private fun OnboardingScreenLayout(
    currentPage: Int,
    onNextClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    pagerContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppColors.current.background)
            .padding(24.dp)
    ) {
        // Skip button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            SecondaryButton(
                text = "Skip",
                onClick = onSkipClicked,
                modifier = Modifier.fillMaxWidth(0.3f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Pager content
        pagerContent()

        Spacer(modifier = Modifier.height(32.dp))

        // Page indicators
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(onboardingPages.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (index == currentPage) 12.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentPage) LocalAppColors.current.primary
                            else LocalAppColors.current.divider
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Next/Get Started button
        PrimaryButton(
            text = if (currentPage == onboardingPages.size - 1) "Get Started" else "Next",
            onClick = onNextClicked
        )
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(page.iconRes),
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            tint = LocalAppColors.current.primary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = ClashDisplay
            ),
            color = LocalAppColors.current.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = LocalAppColors.current.editText,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OnboardingScreenPreview() {
    AquaIntelTheme {
        OnboardingScreenLayout(
            currentPage = 0,
            onNextClicked = {},
            onSkipClicked = {},
            pagerContent = {
                Box() {
                    OnboardingPageContent(page = onboardingPages[0])
                }
            }
        )
    }
}
