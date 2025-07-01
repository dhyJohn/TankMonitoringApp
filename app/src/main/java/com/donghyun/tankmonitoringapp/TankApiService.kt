package com.donghyun.tankmonitoringapp

import com.donghyun.tankmonitoringapp.model.TankData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TankApiService {
    // ✅ 로그인 (JWT 토큰 요청)
    @POST("api/token/")
    @Headers("Content-Type: application/json")
    fun login(
        @Body request: LoginRequest
    ): Call<TokenResponse>

    // ✅ 실시간 탱크 데이터 조회
    @GET("latest-tank-data/")
    fun getLatestTanks(
        @Header("Authorization") authHeader: String
    ): Call<List<TankData>>

    @GET("/api/tank/history")
    fun getHistoricalTanks(
        @Header("Authorization") authHeader: String
    ): Call<List<TankData>>

    @GET("history-data/")
    fun getHistoryData(
        @Header("Authorization") authHeader: String,
        @Query("tank_id") tankId: String?,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<HistoryResponse>


}

