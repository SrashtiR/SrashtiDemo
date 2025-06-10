package com.example.srashtidemoapp.presentation.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.srashtidemoapp.R
import com.example.srashtidemoapp.databinding.ActivityMainBinding
import com.example.srashtidemoapp.domain.model.Holding
import com.example.srashtidemoapp.presentation.ui.adapter.HoldingsAdapter
import com.example.srashtidemoapp.presentation.viewmodel.HoldingsViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: HoldingsViewModel by viewModels()
    private lateinit var adapter: HoldingsAdapter
    private var isSummaryVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupSummaryToggle()

        observeViewModel()
        viewModel.fetchHoldings()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSummaryToggle() {
        binding.toggleSummary.setOnClickListener {
            isSummaryVisible = !isSummaryVisible
            binding.summaryCard.visibility = if (isSummaryVisible) View.VISIBLE else View.GONE
            binding.toggleSummary.text = if (isSummaryVisible) "Profit & Loss ▲" else "Profit & Loss ▼"
        }
    }

    private fun observeViewModel() {
        viewModel.holdings.observe(this) { holdings: List<Holding> ->
            adapter = HoldingsAdapter(holdings)
            binding.recyclerView.adapter = adapter
        }

        viewModel.summary.observe(this) { summary ->
            binding.currentValueText.text = getString(R.string.current_value, summary.currentValue)
            binding.totalInvestmentText.text = getString(R.string.total_investment, summary.totalInvestment)
            binding.totalPNLText.text = getString(R.string.total_pnl, summary.totalPNL)
            binding.todaysPNLText.text = getString(R.string.todays_pnl, summary.todayPNL)
        }
    }

}