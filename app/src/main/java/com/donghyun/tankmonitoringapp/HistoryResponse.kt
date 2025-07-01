package com.donghyun.tankmonitoringapp

import com.donghyun.tankmonitoringapp.model.TankData
import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    val total: Int,
    val page: Int,
    val limit: Int,
    @SerializedName("total_pages") val totalPages: Int,
    val results: List<TankData>
)
