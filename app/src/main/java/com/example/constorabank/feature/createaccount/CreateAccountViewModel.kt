package com.example.constorabank.feature.createaccount

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor() : ViewModel() {
    private val minPasswordLength = 8
    private val emailMustContain = "@"

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun areCredentialsValid(email: String, password: String) =
        email.contains(emailMustContain)
                && password.length >= minPasswordLength
}