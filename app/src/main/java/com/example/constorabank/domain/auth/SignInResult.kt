package com.example.constorabank.domain.auth

sealed interface SignInResult {
    data object Success : SignInResult
    data class Failure(val error: SignInError) : SignInResult
}