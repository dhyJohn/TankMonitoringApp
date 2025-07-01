package com.example.tankmonitoring

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.donghyun.tankmonitoringapp.HistoryResponse
import com.donghyun.tankmonitoringapp.RetrofitClient
import com.donghyun.tankmonitoringapp.databinding.FragmentHistoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: TankHistoryAdapter

    private var currentPage = 1
    private var totalPages = 1
    private var selectedGroup: String? = null
    private var authHeader: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = TankHistoryAdapter()
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.historyRecyclerView.adapter = adapter

        val groups = listOf("전체", "1번", "2번", "3번", "4번", "5번", "6번")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, groups).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.groupSpinner.adapter = spinnerAdapter

        val token = requireActivity()
            .getSharedPreferences("auth", Context.MODE_PRIVATE)
            .getString("access_token", null)

        if (token != null) {
            authHeader = "Bearer $token"


            binding.groupSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    selectedGroup = if (position == 0) null else position.toString()
                    currentPage = 1
                    fetchHistory()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            binding.btnPrev.setOnClickListener {
                if (currentPage > 1) {
                    currentPage--
                    fetchHistory()
                }
            }

            binding.btnNext.setOnClickListener {
                if (currentPage < totalPages) {
                    currentPage++
                    fetchHistory()
                }
            }
        }
    }

    private fun fetchHistory() {
        val token = authHeader ?: return
        val tankId = selectedGroup
        val limit = 20

        RetrofitClient.instance.getHistoryData(token, tankId, currentPage, limit)
            .enqueue(object : Callback<HistoryResponse> {
                override fun onResponse(call: Call<HistoryResponse>, response: Response<HistoryResponse>) {
                    if (response.isSuccessful) {
                        val history = response.body()
                        history?.let {
                            totalPages = it.totalPages
                            adapter.submitList(it.results)
                            updateButtons()
                        }
                    } else {
                        Log.e("API", "응답 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                    Log.e("API", "요청 실패: ${t.message}")
                }
            })
    }

    private fun updateButtons() {
        binding.btnPrev.isEnabled = currentPage > 1
        binding.btnNext.isEnabled = currentPage < totalPages
        binding.tvPageInfo.text = "페이지 $currentPage / $totalPages"
    }
}
