package com.aries.testalfa.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnection{
    companion object{
        fun isNetworkConnection(context: Context): Boolean{
            var connectionManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectionManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }
    }
}