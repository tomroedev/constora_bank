package com.example.constorabank.core

import android.util.Log
import com.example.constorabank.BuildConfig

object L {
    private fun getTag(): String {
        val stackTrace = Thread.currentThread().stackTrace
        return stackTrace.getOrNull(4)
            ?.className
            ?.substringAfterLast('.') // Extracts just the class name
            ?.removeSuffix("Kt") // Removes "Kt" if present
            ?.replace(Regex("\\$.*"), "") // Remove lambda/coroutine suffixes
            ?: "Unknown Class"
    }

    fun d(message: String?) {
        if (BuildConfig.DEBUG) Log.d("AppLog", "[${getTag()}] ${message ?: "null"}")
    }

    fun e(message: String?) {
        if (BuildConfig.DEBUG) Log.e("AppLog", "[${getTag()}] ${message ?: "null"}")
    }

    fun i(message: String?) {
        if (BuildConfig.DEBUG) Log.i("AppLog", "[${getTag()}] ${message ?: "null"}")
    }

    fun w(message: String?) {
        if (BuildConfig.DEBUG) Log.w("AppLog", "[${getTag()}] ${message ?: "null"}")
    }
}