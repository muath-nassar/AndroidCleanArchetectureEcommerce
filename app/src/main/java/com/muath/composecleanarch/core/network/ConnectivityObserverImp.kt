package com.muath.composecleanarch.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityObserverImp @Inject constructor(
    context: Context,
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observeConnectivity(): Flow<ConnectivityObserver.Status> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(ConnectivityObserver.Status.AVAILABLE)
            }

            override fun onLost(network: Network) {
                trySend(ConnectivityObserver.Status.LOST)
            }

            override fun onUnavailable() {
                trySend(ConnectivityObserver.Status.UNAVAILABLE)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                trySend(ConnectivityObserver.Status.LOSING)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)

        // Emit initial status
        trySend(if (isConnected()) ConnectivityObserver.Status.AVAILABLE else ConnectivityObserver.Status.UNAVAILABLE)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    override suspend fun isConnected(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}
