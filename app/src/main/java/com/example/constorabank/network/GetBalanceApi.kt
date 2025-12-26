package com.example.constorabank.network

import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Retrofit API for calling the Supabase get-balance edge function.
 */
interface GetBalanceApi {
    @Headers("Content-Type: application/json")
    @POST("get-balance")
    suspend fun getBalance(): GetBalanceResponse
}