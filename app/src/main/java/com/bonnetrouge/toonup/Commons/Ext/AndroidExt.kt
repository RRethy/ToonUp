package com.bonnetrouge.toonup.Commons.Ext

import android.graphics.Rect
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View

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

