package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.Fragment

fun Fragment.ifAdded(action: () -> Unit) {
    if (isAdded) action()
}

fun android.app.Fragment.ifAdded(action: () -> Unit) {
    if (isAdded) action()
}
