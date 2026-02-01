package com.example.constorabank.network

import com.example.constorabank.core.common.util.L
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val SUPABASE_BASE_URL = "https://esqzalsarannoiqlmgko.functions.supabase.co/"
private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Adds the Firebase ID token as the Authorization header for every request.
 * Edge functions use this token to authenticate the user.
 * This is instead of using/storing anon_key.
 */
class FirebaseAuthInterceptor(
    private val tokenProvider: () -> String?
) : Interceptor {
    /**
     * OkHttp automatically calls intercept() for every network request made by Retrofit.
     * We don’t call intercept() manually.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider()

        L.i(
            "FirebaseAuthInterceptor: ${originalRequest.method} " +
                    "${originalRequest.url} – token is ${if (token != null) "PRESENT" else "MISSING"}"
        )

        val newRequest = if (token != null) {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}

private val MOSHI: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun createSupabaseRetrofit(
    tokenProvider: () -> String?
): Retrofit {
    val client = OkHttpClient.Builder()
        .addInterceptor(FirebaseAuthInterceptor(tokenProvider))
        .build()

    return Retrofit.Builder()
        .baseUrl(SUPABASE_BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}