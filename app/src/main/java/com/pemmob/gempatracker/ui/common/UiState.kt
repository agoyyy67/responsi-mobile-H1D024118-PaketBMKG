package com.pemmob.gempatracker.ui.common

import com.pemmob.gempatracker.data.model.GempaItem

sealed interface UiState {
    object Loading : UiState
    data class Success(val data: List<GempaItem>) : UiState
    data class Error(val message: String) : UiState
}