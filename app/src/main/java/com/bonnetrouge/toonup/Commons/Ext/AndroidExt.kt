package com.bonnetrouge.toonup.Commons.Ext

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.util.DisplayMetrics
import android.widget.Toast

fun convertToPixels(sizeInDp: Double): Double {
    val resources = app.getResources()
    val metrics = resources.getDisplayMetrics()
    return sizeInDp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertToDp(sizeInPixels: Int): Float {
    val resources = app.getResources()
    val metrics = resources.getDisplayMetrics()
    return sizeInPixels / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun getDisplayWidth(): Int = app.resources.displayMetrics.widthPixels

fun getDisplayHeight(): Int = app.resources.displayMetrics.heightPixels

fun resString(resId: Int) = app.getString(resId)

fun postDelayed(milliDelay: Long, action: () -> Unit) {
    Handler().postDelayed({
        action()
    }, milliDelay)
}

fun isInViewBounds(view: View, x: Int, y: Int): Boolean {
    val outRect = Rect()
    val location = IntArray(2)
    view.getDrawingRect(outRect)
    view.getLocationOnScreen(location)
    outRect.offset(location[0], location[1])
    return outRect.contains(x, y)
}

fun <T> lazyAndroid(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

