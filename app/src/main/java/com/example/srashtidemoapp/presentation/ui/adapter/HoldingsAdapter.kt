package com.example.srashtidemoapp.presentation.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.srashtidemoapp.R
import com.example.srashtidemoapp.domain.model.Holding

class HoldingsAdapter(
    private val holdings: List<Holding>
) : RecyclerView.Adapter<HoldingsAdapter.HoldingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoldingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_holding, parent, false)
        return HoldingViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoldingViewHolder, position: Int) {
        val item = holdings[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = holdings.size

    inner class HoldingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val symbolText: TextView = itemView.findViewById(R.id.symbolText)
        private val quantityText: TextView = itemView.findViewById(R.id.quantityText)
        private val ltpText: TextView = itemView.findViewById(R.id.ltpText)
        private val pnlText: TextView = itemView.findViewById(R.id.pnlText)

        fun bind(item: Holding) {
            val investment = item.avgPrice * item.quantity
            val pnl = (item.ltp * item.quantity) - investment

            symbolText.text = item.symbol
            quantityText.text = "NET QTY: ${item.quantity}"
            ltpText.text = "₹%.2f".format(item.ltp)
            pnlText.text = "P&L: ₹%.2f".format(pnl)

            // Apply green/red color based on P&L
            pnlText.setTextColor(
                if (pnl >= 0) Color.parseColor("#4CAF50") else Color.parseColor("#F44336")
            )
        }
    }
}