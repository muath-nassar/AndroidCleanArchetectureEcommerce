package com.muath.composecleanarch.core.common

/**
 * This a simple wrapper to wrap the success across the app
 */
sealed class Result<out T> {

    // Success containing the value
    data class Ok<T>(val value: T): Result<T>()

    // Error containing the error as AppError
    data class Err(val error: AppError): Result<Nothing>()

    //------------ Helpers -------------------

    fun getOrNull(): T?{
        return if (this is Ok) value else null
    }

    fun errorOrNull(): AppError?{
        return if(this is Err) error else null
    }

    fun <R>map(transform: (T) -> R): Result<R>{
        return when(this){
            is Ok -> Ok(transform(value))
            is Err -> this
        }

    }

}