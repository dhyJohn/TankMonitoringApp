package com.donghyun.tankmonitoringapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // XML 뷰 연결
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvResult = findViewById(R.id.tvResult)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val loginRequest = LoginRequest(username, password)

            RetrofitClient.instance.login(loginRequest).enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    if (response.isSuccessful) {
                        val token = response.body()?.access
                        if (token != null) {
                            Log.d("TOKEN", "Access Token: $token")
                            tvResult.text = "✅ 로그인 성공!"

                            // ✅ 1. 토큰 저장
                            val sharedPref = getSharedPreferences("auth", Context.MODE_PRIVATE)
                            sharedPref.edit().putString("access_token", token).apply()

                            // ✅ 2. 다음 화면 이동 (위치 OK)
                            val intent = Intent(this@LoginActivity, DashboardContainerActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            tvResult.text = "❌ 로그인 실패: 토큰 없음"
                        }
                    } else {
                        tvResult.text = "❌ 로그인 실패: ${response.code()}"
                    }

                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    tvResult.text = "🚫 네트워크 오류: ${t.message}"
                }
            })
        }
    }
}
