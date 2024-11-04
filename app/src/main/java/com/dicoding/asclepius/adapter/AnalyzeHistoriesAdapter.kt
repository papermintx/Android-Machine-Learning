package com.dicoding.asclepius.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ItemAnalyzeHistoryBinding
import com.dicoding.asclepius.domain.model.AnalyzeHistories

class AnalyzeHistoriesAdapter(
    private var analyzeHistories: List<AnalyzeHistories>,
    private val onDeleteClick: (AnalyzeHistories) -> Unit
) : RecyclerView.Adapter<AnalyzeHistoriesAdapter.AnalyzeHistoriesViewHolder>() {

    inner class AnalyzeHistoriesViewHolder(private val binding: ItemAnalyzeHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: AnalyzeHistories) {
            with(binding) {
                tvResult.text = history.result
                tvDate.text = history.date
                tvScore.text = history.score

                Glide.with(itemView.context)
                    .load(history.image)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView)

                btnDelete.setOnClickListener {
                    onDeleteClick(history)
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyzeHistoriesViewHolder {
        val binding = ItemAnalyzeHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AnalyzeHistoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnalyzeHistoriesViewHolder, position: Int) {
        holder.bind(analyzeHistories[position])
    }


    override fun getItemCount(): Int = analyzeHistories.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newHistories: List<AnalyzeHistories>) {
        analyzeHistories = newHistories
        notifyDataSetChanged()
    }
}

