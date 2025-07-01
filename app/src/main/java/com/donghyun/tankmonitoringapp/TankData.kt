package com.donghyun.tankmonitoringapp.model

data class TankData(
    val tank_id: Int,
    val name: String,
    val temperature: Float,
    val fuel_level: Float,
    val water_level: Float,
    val capacity: Float,
    val timestamp: String
)

