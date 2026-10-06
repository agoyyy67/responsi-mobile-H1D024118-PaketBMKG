package com.pemmob.gempatracker.data.network

import com.pemmob.gempatracker.data.model.GempaResponse
import retrofit2.http.GET

interface ApiService {
    @GET("DataMKG/TEWS/gempaterkini.json")
    suspend fun getGempaTerkini(): GempaResponse
}