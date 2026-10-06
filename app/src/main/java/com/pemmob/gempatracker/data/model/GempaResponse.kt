package com.pemmob.gempatracker.data.model

import com.google.gson.annotations.SerializedName

data class GempaResponse(
    @SerializedName("Infogempa")
    val infogempa: InfoGempaData
)

data class InfoGempaData(
    @SerializedName("gempa")
    val gempa: List<GempaItem>
)

data class GempaItem(
    @SerializedName("Tanggal") val tanggal: String,
    @SerializedName("Jam") val jam: String,
    @SerializedName("DateTime") val dateTime: String?,
    @SerializedName("Coordinates") val coordinates: String,
    @SerializedName("Lintang") val lintang: String,
    @SerializedName("Bujur") val bujur: String,
    @SerializedName("Magnitude") val magnitude: String,
    @SerializedName("Kedalaman") val kedalaman: String,
    @SerializedName("Wilayah") val wilayah: String,
    @SerializedName("Potensi") val potensi: String,
    @SerializedName("Dirasakan") val dirasakan: String?
)