package com.example.adviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adviceapp.R

class AdviceAdapter(private val adviceList: List<String>) :
    RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_advice, parent, false)
        return AdviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
        val advice = adviceList[position]
        holder.adviceTextView.text = advice
    }

    override fun getItemCount() = adviceList.size

    class AdviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adviceTextView: TextView = itemView.findViewById(R.id.tvAdviceText)
    }
}