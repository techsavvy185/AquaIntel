package com.aquaintel.app.ui.screens.main.gemini

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquaintel.app.data.repository.GeminiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeminiChatViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GeminiChatUiState())
    val uiState: StateFlow<GeminiChatUiState> = _uiState.asStateFlow()

    fun onUserInputChanged(input: String) {
        _uiState.update { it.copy(userInput = input) }
    }

    fun sendMessage() {
        val currentInput = _uiState.value.userInput
        if (currentInput.isBlank()) return

        // Add user message
        val userMessage = ChatMessage(text = currentInput, isUser = true)
        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                userInput = "",
                isLoading = true,
                error = null
            )
        }

        // Get AI response
        viewModelScope.launch {
            geminiRepository.sendMessage(currentInput)
                .onSuccess { response ->
                    val botMessage = ChatMessage(text = response, isUser = false)
                    _uiState.update {
                        it.copy(
                            messages = it.messages + botMessage,
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.localizedMessage ?: "Unknown error occurred",
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
