package com.pemmob.gempatracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob.gempatracker.data.model.GempaItem
import com.pemmob.gempatracker.data.repository.GempaRepository
import com.pemmob.gempatracker.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GempaViewModel(
    private val repository: GempaRepository
) : ViewModel() {

    private val _rawState = MutableStateFlow<UiState>(UiState.Loading)

    // State untuk input query pencarian
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Memfilter data Success berdasarkan input Wilayah secara reaktif
    val uiState: StateFlow<UiState> = combine(_rawState, _searchQuery) { state, query ->
        if (state is UiState.Success) {
            if (query.isBlank()) {
                state
            } else {
                val filteredList = state.data.filter { item ->
                    item.wilayah.contains(query, ignoreCase = true)
                }
                UiState.Success(filteredList)
            }
        } else {
            state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    init {
        fetchGempaData()
    }

    fun fetchGempaData() {
        viewModelScope.launch {
            _rawState.value = UiState.Loading
            try {
                val result = repository.getDaftarGempa()
                _rawState.value = UiState.Success(result)
            } catch (e: Exception) {
                _rawState.value = UiState.Error(e.localizedMessage ?: "Gagal terhubung ke server BMKG.")
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
}