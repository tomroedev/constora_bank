package com.example.constorabank.network

/**
 * Creates a Retrofit API instance for calling the get-balance function.
 * Pass in a function to get the token rather than the token itself as tokens can go stale.
 */
fun createGetBalanceApi(
    tokenProvider: () -> String?
): GetBalanceApi =
    createSupabaseRetrofit(tokenProvider).create(GetBalanceApi::class.java)