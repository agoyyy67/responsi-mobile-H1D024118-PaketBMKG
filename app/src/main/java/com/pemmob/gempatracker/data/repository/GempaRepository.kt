package com.pemmob.gempatracker.data.repository

import com.pemmob.gempatracker.data.model.GempaItem
import com.pemmob.gempatracker.data.network.ApiService

class GempaRepository(private val apiService: ApiService) {
    suspend fun getDaftarGempa(): List<GempaItem> {
        return apiService.getGempaTerkini().infogempa.gempa
    }
}