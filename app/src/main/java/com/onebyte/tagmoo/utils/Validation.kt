package com.onebyte.tagmoo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.onebyte.tagmoo.R

object Validation {

    /**
     * Validate network connection status
     */
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
            }
            return true
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.internet_not_connected),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
    }
}