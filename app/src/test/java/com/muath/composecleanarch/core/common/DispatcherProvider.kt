package com.muath.composecleanarch.core.common

import kotlinx.coroutines.test.StandardTestDispatcher
class TestDispatcherProvider: DispatcherProvider {

    private val testDispatcher = StandardTestDispatcher()

    // Mocking the dispatchers for testing
    override val io = testDispatcher
    override val main = testDispatcher
    override val default = testDispatcher
}