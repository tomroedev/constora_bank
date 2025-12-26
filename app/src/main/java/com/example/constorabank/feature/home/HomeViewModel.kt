package com.example.constorabank.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.getbalance.GetBalanceResult
import com.example.constorabank.domain.getbalance.GetBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
) : ViewModel() {

    private val _balance = MutableStateFlow("")
    val balance: StateFlow<String> = _balance

    init {
        getBalance()
    }

    fun getBalance() {
        L.i("getBalance(): entered")
        viewModelScope.launch {
            try {
                val result = getBalanceUseCase.invoke()
                L.i("getBalance(): RESULT: $result")
                when (result) {
                    is GetBalanceResult.Success -> {
                        L.i("getBalance(): balance = ${result.balance}")
                        _balance.value = result.balance
                    }
                    is GetBalanceResult.Failure -> {
                        L.d("getBalance(): error ${result.errorCode}")
                        _balance.value = "Error"
                    }
                }
            } catch (e: Exception) {
                L.e("getBalance(): result exception $e")
                _balance.value = "Error"
            }
        }
    }

}