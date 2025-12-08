package com.example.constorabank.domain.auth

interface TokenProvider {
    fun getToken(): String?
}