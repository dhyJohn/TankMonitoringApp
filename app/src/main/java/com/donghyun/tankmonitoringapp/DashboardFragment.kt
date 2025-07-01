package com.donghyun.tankmonitoringapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.donghyun.tankmonitoringapp.adapter.TankAdapter
import com.donghyun.tankmonitoringapp.databinding.FragmentDashboardBinding
import com.donghyun.tankmonitoringapp.model.TankData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var adapter: TankAdapter
    private val tankList = mutableListOf<TankData>()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var refreshRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TankAdapter(tankList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        val token = requireActivity()
            .getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("access_token", null)

        if (token != null) {
            startAutoRefresh("Bearer $token")
        }
    }

    private fun startAutoRefresh(authHeader: String) {
        refreshRunnable = object : Runnable {
            override fun run() {
                fetchTankData(authHeader)
                handler.postDelayed(this, 5000)
            }
        }
        handler.post(refreshRunnable)
    }

    private fun fetchTankData(authHeader: String) {
        RetrofitClient.instance.getLatestTanks(authHeader)
            .enqueue(object : Callback<List<TankData>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<List<TankData>>, response: Response<List<TankData>>) {
                    Log.d("DASHBOARD", "응답 코드: ${response.code()}")
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("DASHBOARD", "데이터 개수: ${result?.size}")
                        Log.d("DASHBOARD", "응답 내용: $result")

                        tankList.clear()
                        if (result != null) {
                            tankList.addAll(result)
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.e("DASHBOARD", "응답 실패: ${response.code()}")
                        Log.e("DASHBOARD", "에러 내용: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<List<TankData>>, t: Throwable) {
                    Log.e("DASHBOARD", "요청 실패: ${t.message}", t)
                }
            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(refreshRunnable)
    }
}
