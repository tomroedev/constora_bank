package com.example.constorabank.feature.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.auth.SignInError
import com.example.constorabank.domain.auth.SignInResult
import com.example.constorabank.domain.auth.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Only check for email validity here as this won't change.
 * Don't check for password validity (as we do when creating an account) as this is open to be changed in the future.
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _signInResult = MutableSharedFlow<SignInResult>()
    val signInResult: SharedFlow<SignInResult> = _signInResult

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = try {
                signInUseCase(email.trim(), password)
            } catch (e: Exception) {
                L.e("Sign in exception", e)
                SignInResult.Failure(SignInError.UNKNOWN)
            }

            _signInResult.emit(result)
            _isLoading.value = false

            when (result) {
                is SignInResult.Success -> {
                    L.i("Signed in successfully")
                }

                is SignInResult.Failure -> {
                    L.e("Signing in failed: ${result.error}")
                }
            }
        }
    }

}