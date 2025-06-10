package com.example.srashtidemoapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.srashtidemoapp.domain.model.Holding
import com.example.srashtidemoapp.domain.model.PortfolioSummary
import com.example.srashtidemoapp.domain.repository.HoldingsRepository
import com.example.srashtidemoapp.domain.usecase.CalculatePortfolioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val repository: HoldingsRepository,
    private val calculatePortfolioUseCase: CalculatePortfolioUseCase
) : ViewModel() {

    private val _holdings = MutableLiveData<List<Holding>>()
    val holdings: LiveData<List<Holding>> = _holdings

    private val _summary = MutableLiveData<PortfolioSummary>()
    val summary: LiveData<PortfolioSummary> = _summary

    fun fetchHoldings() {
        viewModelScope.launch {
            try {
                val holdingsList = repository.getHoldings()
                _holdings.value = holdingsList
                _summary.value = calculatePortfolioUseCase.calculateSummary(holdingsList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
