package com.example.srashtidemoapp.presentation.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
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

        binding.topAppBar.title = getString(R.string.portfolio)

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


            val arrow = if (isSummaryVisible) "▲" else "▼"
            binding.summaryLabel.text = getString(R.string.profit_loss, arrow)
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

            // Update summary bar total PNL (toggleSummary section)
            binding.summaryTotalPnl.text = getString(R.string.amount_format, summary.totalPNL)

            // Change color based on profit or loss
            val colorRes = if (summary.totalPNL >= 0) R.color.profitGreen else R.color.lossRed
            binding.summaryTotalPnl.setTextColor(resources.getColor(colorRes, theme))
        }



        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        viewModel.errorMessage.observe(this) { msg ->
            msg?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

}