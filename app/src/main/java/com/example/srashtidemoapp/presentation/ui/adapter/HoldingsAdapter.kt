package com.example.srashtidemoapp.presentation.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.srashtidemoapp.R
import com.example.srashtidemoapp.databinding.ItemHoldingBinding
import com.example.srashtidemoapp.domain.model.Holding

class HoldingsAdapter(
    private val holdings: List<Holding>
) : RecyclerView.Adapter<HoldingsAdapter.HoldingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val binding = ItemHoldingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HoldingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        holder.bind(holdings[position])
    }

    override fun getItemCount(): Int = holdings.size

    inner class HoldingViewHolder(
        private val binding: ItemHoldingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Holding) {
            binding.item = item

            val investment = item.avgPrice * item.quantity
            val pnl = (item.ltp * item.quantity) - investment

            val pnlText = binding.root.context.getString(R.string.pnl_format, pnl)
            binding.pnlText.text = pnlText

            binding.pnlText.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    if (pnl >= 0) R.color.profitGreen else R.color.lossRed
                )
            )
        }
    }
}