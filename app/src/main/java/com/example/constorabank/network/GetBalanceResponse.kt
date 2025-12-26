package com.example.constorabank.network

/**
 * Response body returned by the get-balance edge function.
 *
 * When success is true:
 * we get the balance.
 *
 * When success is false:
 * errorCode and message may contain more details.
 */
data class GetBalanceResponse(
    val success: Boolean,
    val balance: Long?,
    val errorCode: String?,
    val message: String?
)