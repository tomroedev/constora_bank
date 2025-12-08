package com.example.constorabank.network

/**
 * Response body returned by the debit-balance edge function.
 *
 * When success is true:
 * newBalance is the updated balance.
 *
 * When success is false:
 * errorCode and message may contain more details.
 */
data class DebitResponse(
    val success: Boolean,
    val newBalance: Long?,
    val errorCode: String?,
    val message: String?
)