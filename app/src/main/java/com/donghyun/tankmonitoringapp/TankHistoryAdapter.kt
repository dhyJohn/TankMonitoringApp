package com.example.tankmonitoring

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donghyun.tankmonitoringapp.R
import com.donghyun.tankmonitoringapp.model.TankData

class TankHistoryAdapter : RecyclerView.Adapter<TankHistoryAdapter.ViewHolder>() {

    private val items = mutableListOf<TankData>()

    fun submitList(newItems: List<TankData>?) {
        items.clear()
        if (newItems != null) {
            items.addAll(newItems)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tankId: TextView = itemView.findViewById(R.id.tankIdText)
        private val name: TextView = itemView.findViewById(R.id.nameText)
        private val temp: TextView = itemView.findViewById(R.id.temperatureText)
        private val fuel: TextView = itemView.findViewById(R.id.fuelLevelText)
        private val water: TextView = itemView.findViewById(R.id.waterLevelText)
        private val capacity: TextView = itemView.findViewById(R.id.capacityText)
        private val timestamp: TextView = itemView.findViewById(R.id.timestampText)

        fun bind(item: TankData) {
            tankId.text = "탱크 ID: ${item.tank_id}"
            name.text = "이름: ${item.name}"
            temp.text = "온도: %.2f°C".format(item.temperature)
            fuel.text = "연료: %.2f L".format(item.fuel_level)
            water.text = "물수위: %.2f cm".format(item.water_level)
            capacity.text = "용량: %.2f L".format(item.capacity)
            timestamp.text = "저장 시간: ${item.timestamp}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tank_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
