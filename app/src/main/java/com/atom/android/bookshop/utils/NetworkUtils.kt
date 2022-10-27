package com.atom.android.bookshop.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest

fun registerNetwork(
    connectivityManager: ConnectivityManager?,
    onConnectedInternet: () -> Unit,
    onLostInternet: () -> Unit
) {
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
    connectivityManager?.requestNetwork(
        networkRequest,
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onConnectedInternet()
            }
            override fun onLost(network: Network) {
                super.onLost(network)
                onLostInternet()
            }
        })
}

fun isConnectedToInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivityManager.allNetworkInfo
    info.forEach {
        if (it.state == NetworkInfo.State.CONNECTED) return true
    }
    return false
}
