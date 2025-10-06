package com.aquaintel.app.ui.screens.auth.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    private val _onboardingUiState = MutableStateFlow(OnboardingUiState())
    val onboardingUiState: StateFlow<OnboardingUiState> = _onboardingUiState.asStateFlow()

    fun nextPage() {
        _onboardingUiState.update {
            it.copy(currentPage = (it.currentPage + 1).coerceAtMost(it.totalPages - 1))
        }
    }

    fun previousPage() {
        _onboardingUiState.update {
            it.copy(currentPage = (it.currentPage - 1).coerceAtLeast(0))
        }
    }

    fun setPage(page: Int) {
        _onboardingUiState.update {
            it.copy(currentPage = page.coerceIn(0, it.totalPages - 1))
        }
    }
}
