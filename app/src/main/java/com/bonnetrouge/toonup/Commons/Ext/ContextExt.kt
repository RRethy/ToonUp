package com.bonnetrouge.toonup.Commons.Ext

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

fun Context.isConnected(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetworkInfo
    return activeNetwork != null &&
            activeNetwork.isConnectedOrConnecting();
}

fun Context.longToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.longToast(msgId: Int) {
    Toast.makeText(this, resString(msgId), Toast.LENGTH_LONG).show()
}

fun Context.shortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.shortToast(msgId: Int) {
    Toast.makeText(this, resString(msgId), Toast.LENGTH_SHORT).show()
}
