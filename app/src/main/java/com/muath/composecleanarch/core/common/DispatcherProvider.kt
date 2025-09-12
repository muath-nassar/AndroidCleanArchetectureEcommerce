package com.muath.composecleanarch.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Abstraction over coroutine dispatchers.
 * Makes coroutines testable by allowing us to inject custom dispatchers in JUnit tests.
 */
interface DispatcherProvider {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
}

/**
 *  To use in the App
 */

class DefaultDispatcherProvider: DispatcherProvider{
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val main: CoroutineDispatcher =  Dispatchers.Main
    override val default: CoroutineDispatcher = Dispatchers.Default
}