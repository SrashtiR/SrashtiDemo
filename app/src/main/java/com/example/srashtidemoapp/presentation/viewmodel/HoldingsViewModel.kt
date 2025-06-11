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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchHoldings() {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val holdingsList = runCatching {
                repository.getHoldings()
            }.onFailure {
                it.printStackTrace()
                _errorMessage.value = "Unable to fetch data from network. Showing offline data."
            }.getOrDefault(emptyList())

            if (holdingsList.isNotEmpty()) {
                _holdings.value = holdingsList
                _summary.value = calculatePortfolioUseCase.calculateSummary(holdingsList)
            } else {
                _errorMessage.value = "No data available."
            }

            _loading.value = false
        }
    }
}
