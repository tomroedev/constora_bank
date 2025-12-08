package com.example.constorabank.data.transferfunds

import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.auth.TokenProvider
import com.example.constorabank.domain.transferfunds.TransferFundsError
import com.example.constorabank.domain.transferfunds.TransferFundsRepository
import com.example.constorabank.domain.transferfunds.TransferFundsResult
import com.example.constorabank.network.DebitRequest
import com.example.constorabank.network.createDebitBalanceApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseTransferFundsRepository @Inject constructor(
    val tokenProvider: TokenProvider
) : TransferFundsRepository {

    private val api = createDebitBalanceApi(tokenProvider::getToken)

    override suspend fun transferFunds(amount: Long): TransferFundsResult {
        L.i("transferFunds(): Attempt to transfer £$amount")
        return try {
            val response = api.debitBalance(
                DebitRequest(
                    amount = amount
                )
            )
            L.i("transferFunds(): Response: $response")
            if (response.success) {
                TransferFundsResult.Success
            } else {
                TransferFundsResult.Failure(mapSupabaseError(response.errorCode))
            }
        } catch (e: Exception) {
            L.e("transferFunds(): Error calling debit-balance $e")
            TransferFundsResult.Failure(TransferFundsError.UNKNOWN_ERROR)
        }
    }

    private fun mapSupabaseError(errorCode: String?): TransferFundsError =
        when (errorCode) {
            "UNKNOWN_ERROR" -> TransferFundsError.UNKNOWN_ERROR
            "INVALID_AMOUNT" -> TransferFundsError.INVALID_AMOUNT
            "INSUFFICIENT_FUNDS" -> TransferFundsError.INSUFFICIENT_FUNDS
            "USER_NOT_FOUND" -> TransferFundsError.INTERNAL_ERROR
            "ONLY_POST_ALLOWED" -> TransferFundsError.INTERNAL_ERROR
            "MISSING_AUTH_HEADER" -> TransferFundsError.INTERNAL_ERROR
            "INVALID_FIREBASE_ID_TOKEN" -> TransferFundsError.INTERNAL_ERROR
            "AUTHENTICATED_USER_UID_ISSUE" -> TransferFundsError.INTERNAL_ERROR
            "INVALID_JSON_BODY" -> TransferFundsError.INTERNAL_ERROR
            "REQUEST_BODY_MUST_BE_JSON" -> TransferFundsError.INTERNAL_ERROR
            "MISSING_OR_INVALID_AMOUNT" -> TransferFundsError.INTERNAL_ERROR
            else -> TransferFundsError.UNKNOWN_ERROR
        }
}


