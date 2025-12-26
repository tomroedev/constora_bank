package com.example.constorabank.data.getbalance

import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.auth.TokenProvider
import com.example.constorabank.domain.getbalance.GetBalanceError
import com.example.constorabank.domain.getbalance.GetBalanceRepository
import com.example.constorabank.domain.getbalance.GetBalanceResult
import com.example.constorabank.network.createGetBalanceApi
import javax.inject.Inject

class SupabaseGetBalanceRepository @Inject constructor(
    private val tokenProvider: TokenProvider
) : GetBalanceRepository {

    private val api = createGetBalanceApi(tokenProvider::getToken)

    override suspend fun getBalance(): GetBalanceResult {
        return try {
            val response = api.getBalance()
            if (response.success && response.balance != null) {
                GetBalanceResult.Success(response.balance.toString())
            } else if (response.success && response.balance == null) {
                // Unexpected state: API reports success but balance is null
                GetBalanceResult.Failure(GetBalanceError.INTERNAL_ERROR)
            } else {
                GetBalanceResult.Failure(mapSupabaseError(response.errorCode))
            }
        } catch (e: Exception) {
            L.e("Failed to get balance from API: ${e.message}")
            GetBalanceResult.Failure(GetBalanceError.UNKNOWN_ERROR)
        }
    }

    private fun mapSupabaseError(errorCode: String?): GetBalanceError =
        when (errorCode) {
            "UNKNOWN_ERROR" -> GetBalanceError.UNKNOWN_ERROR
            "USER_NOT_FOUND" -> GetBalanceError.INTERNAL_ERROR
            "ONLY_POST_ALLOWED" -> GetBalanceError.INTERNAL_ERROR
            "MISSING_AUTH_HEADER" -> GetBalanceError.INTERNAL_ERROR
            "INVALID_FIREBASE_ID_TOKEN" -> GetBalanceError.INTERNAL_ERROR
            "AUTHENTICATED_USER_UID_ISSUE" -> GetBalanceError.INTERNAL_ERROR
            "INVALID_JSON_BODY" -> GetBalanceError.INTERNAL_ERROR
            "REQUEST_BODY_MUST_BE_JSON" -> GetBalanceError.INTERNAL_ERROR
            else -> GetBalanceError.UNKNOWN_ERROR
        }

}