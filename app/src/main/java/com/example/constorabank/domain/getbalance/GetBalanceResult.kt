package com.example.constorabank.domain.getbalance

sealed interface GetBalanceResult {
    data class Success(val balance: String): GetBalanceResult
    data class Failure(val errorCode: GetBalanceError): GetBalanceResult
}