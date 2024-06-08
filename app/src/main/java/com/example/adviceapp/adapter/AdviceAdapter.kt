package com.example.adviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adviceapp.R

class AdviceAdapter(private var advices: MutableList<String>) : RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_advice, parent, false)
        return AdviceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
        holder.tvAdvice.text = advices[position]
    }

    override fun getItemCount(): Int {
        return advices.size
    }

    fun updateData(newAdvices: List<String>) {
        advices.clear()
        advices.addAll(newAdvices)
        notifyDataSetChanged()
    }

    fun addAdvice(advice: String) {
        advices.add(advice)
        notifyDataSetChanged()
    }

    class AdviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAdvice: TextView = itemView.findViewById(R.id.tvAdvice)
    }
}