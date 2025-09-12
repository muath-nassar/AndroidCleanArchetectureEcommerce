package com.muath.composecleanarch.core.network

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    enum class Status{
        AVAILABLE,
        UNAVAILABLE,
        LOST,
        LOSING
    }

    fun observeConnectivity(): Flow<Status>

    suspend fun isConnected(): Boolean
}