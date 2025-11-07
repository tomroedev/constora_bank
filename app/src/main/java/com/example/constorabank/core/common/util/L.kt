package com.example.constorabank.core.common.util

import android.util.Log
import com.example.constorabank.BuildConfig

object L {

    private val loggerClassName = L::class.java.name

    private fun getIdentifiers(): String {
        val stackTrace = Throwable().stackTrace

        // Find first frame that isn't this logger
        val caller = stackTrace.firstOrNull { element ->
            element.className != loggerClassName
        } ?: return "UnknownClass"

        val className = caller.className
            .substringAfterLast('.')   // just class name
            .substringBefore('$')      // remove inner/lambda suffix
            .removeSuffix("Kt")        // remove "Kt" for file classes

        val rawMethod = caller.methodName
        val methodName = when (rawMethod) {
            "invokeSuspend", "invoke", "resumeWith" -> null
            else -> rawMethod
        }

        val lineNumber = caller.lineNumber.takeIf { it >= 0 }?.toString() ?: "?"

        return if (methodName != null) {
            "$className.$methodName():$lineNumber"
        } else {
            "$className:$lineNumber"
        }
    }

    fun d(message: String?, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) Log.d("AppLog", "[${getIdentifiers()}] ${message ?: "null"}", throwable)
    }

    fun e(message: String?, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) Log.e("AppLog", "[${getIdentifiers()}] ${message ?: "null"}", throwable)
    }

    fun i(message: String?, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) Log.i("AppLog", "[${getIdentifiers()}] ${message ?: "null"}", throwable)
    }

    fun w(message: String?, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) Log.w("AppLog", "[${getIdentifiers()}] ${message ?: "null"}", throwable)
    }
}