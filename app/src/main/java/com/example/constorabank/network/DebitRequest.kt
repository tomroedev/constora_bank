package com.example.constorabank.network

/**
 * Request body sent to the debit-balance edge function.
 */
data class DebitRequest(
    val amount: Long
)