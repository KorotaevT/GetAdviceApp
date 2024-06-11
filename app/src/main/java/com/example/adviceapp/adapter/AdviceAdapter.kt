package com.example.adviceapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.adviceapp.R

class AdviceAdapter(private val advices: MutableList<String>) : RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder>() {

    class AdviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adviceTextView: TextView = itemView.findViewById(R.id.adviceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_advice, parent, false)
        return AdviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
        holder.adviceTextView.text = advices[position]
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
        advices.add(0, advice)
        notifyItemInserted(0)
    }
}