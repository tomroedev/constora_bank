package com.example.constorabank.feature.createaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.auth.CreateAccountUseCase
import com.example.constorabank.domain.auth.RegistrationError
import com.example.constorabank.domain.auth.RegistrationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _registrationResult = MutableSharedFlow<RegistrationResult>()
    val registrationResult: SharedFlow<RegistrationResult> = _registrationResult

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun createAccount(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = try {
                createAccountUseCase(email.trim(), password)
            } catch (e: Exception) {
                L.e("Account creation exception", e)
                RegistrationResult.Failure(RegistrationError.UNKNOWN)
            }

            _registrationResult.emit(result)
            _isLoading.value = false

            when (result) {
                is RegistrationResult.Success -> {
                    L.i("Account created successfully")
                }

                is RegistrationResult.Failure -> {
                    L.e("Account creation failed: ${result.error}")
                }
            }
        }
    }

}