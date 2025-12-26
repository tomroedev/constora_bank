package com.example.constorabank.network

/**
 * Creates a Retrofit API instance for calling the debit-balance function.
 * Pass in a function to get the token rather than the token itself as tokens can go stale.
 */
fun createDebitBalanceApi(
    tokenProvider: () -> String?
): DebitBalanceApi =
    createSupabaseRetrofit(tokenProvider).create(DebitBalanceApi::class.java)