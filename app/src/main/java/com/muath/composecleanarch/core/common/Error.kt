package com.muath.composecleanarch.core.common

/**
 * This class is used to represent errors. its main purpose is to keep the UI free from
 * handling exceptions. like (Retrofit, Room, etc)
 */
sealed class AppError {
    sealed class Network: AppError(){
        object NoConnection: Network()
        object TimeOut: Network()
        data class ServerError(val message: String): Network()
        data class ClientError(val message: String): Network()
    }

    // --- Database related ---
    sealed class Database : AppError() {
        object ReadError : Database()
        object WriteError : Database()
    }

    // --- Authentication / Authorization ---
    sealed class Auth : AppError() {
        object Unauthorized : Auth()   // 401
        object Forbidden : Auth()      // 403
    }

    // --- Validation / Business logic ---
    sealed class Validation : AppError() {
        data class InvalidInput(val reason: String) : Validation()
        object OutOfStock : Validation()
    }

    // --- Unknown / fallback ---
    data class Unknown(val cause: Throwable? = null) : AppError()
}