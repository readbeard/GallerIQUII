package com.example.galleriquii

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkStatusMonitor : ConnectivityManager.NetworkCallback() {
    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    fun enable(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onLost(network: Network) {
        networkStatusListener?.onNetworkConnectionChanged(false)
        super.onLost(network)
    }

    override fun onUnavailable() {
        networkStatusListener?.onNetworkConnectionChanged(false)
        super.onUnavailable()
    }

    override fun onAvailable(network: Network) {
        networkStatusListener?.onNetworkConnectionChanged(true)
    }

    interface NetworkStatusListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var networkStatusListener: NetworkStatusListener? = null
    }
}