package com.example.constorabank.core.common.util

object Validation {
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val strongPasswordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9]).{8,}$")

    fun isEmailValid(email: String) =
        emailRegex.matches(email)

    fun areSignInDetailsValid(email: String, password: String) =
        isEmailValid(email) && password.isNotBlank()

    fun areCreateAccountCredentialsValid(email: String, password: String) =
        isEmailValid(email) && strongPasswordRegex.matches(password)
}