package com.example.constorabank.network

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Retrofit API for calling the Supabase debit-balance edge function.
 */
interface DebitBalanceApi {
    @Headers("Content-Type: application/json")
    @POST("debit-balance")
    suspend fun debitBalance(
        @Body request: DebitRequest
    ): DebitResponse
}