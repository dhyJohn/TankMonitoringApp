package com.donghyun.tankmonitoringapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.donghyun.tankmonitoringapp.R
import com.donghyun.tankmonitoringapp.model.TankData

class TankAdapter(private val tankList: List<TankData>) :
    RecyclerView.Adapter<TankAdapter.TankViewHolder>() {

    class TankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTankName: TextView = itemView.findViewById(R.id.tvTankName)
        val tvFuelLevel: TextView = itemView.findViewById(R.id.tvFuelLevel)
        val tvWaterLevel: TextView = itemView.findViewById(R.id.tvWaterLevel)
        val tvTemperature: TextView = itemView.findViewById(R.id.tvTemperature)
        val capacity = itemView.findViewById<TextView>(R.id.capacityText)
        val timestamp = itemView.findViewById<TextView>(R.id.timestampText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tank, parent, false)
        return TankViewHolder(view)
    }

    override fun onBindViewHolder(holder: TankViewHolder, position: Int) {
        val tank = tankList[position]

        holder.tvTankName.text = "탱크 ID: ${tank.tank_id}"
        holder.tvFuelLevel.text = "이름: ${tank.name}"
        holder.tvTemperature.text = "온도: %.2f°C".format(tank.temperature)
        holder.tvFuelLevel.text = "연료: %.2f L".format(tank.fuel_level)
        holder.tvWaterLevel.text = "물수위:  %.2f cm".format(tank.water_level)
        holder.capacity.text = "용량:  %.2f L".format(tank.capacity)
        holder.timestamp.text = "저장 시간: ${tank.timestamp}"


    }

    override fun getItemCount(): Int = tankList.size
}
