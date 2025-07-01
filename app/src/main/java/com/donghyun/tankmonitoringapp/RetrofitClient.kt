package com.donghyun.tankmonitoringapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://172.30.1.54:8000/"  // 실제 서버 IP로 변경

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: TankApiService by lazy {
        retrofit.create(TankApiService::class.java)
    }
}
