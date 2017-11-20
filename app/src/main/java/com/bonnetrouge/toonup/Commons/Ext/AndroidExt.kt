package com.bonnetrouge.toonup.Commons.Ext

import android.graphics.Rect
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
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

/**
 * Faster lazy delegation for Android.
 * Warning: Only use for objects accessed on main thread
 */
fun <T> lazyAndroid(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun sdkAbove(sdkVerson: Int, action: () -> Unit) {
    if (android.os.Build.VERSION.SDK_INT >= sdkVerson){
        action()
    }
}

inline fun sdkAbove(sdkVerson: Int, action: () -> Unit, actionIfBelow: () -> Unit) {
    if (android.os.Build.VERSION.SDK_INT >= sdkVerson){
        action()
    } else{
        actionIfBelow()
    }
}

inline fun <reified T : Parcelable> createParcel(crossinline createFromParcel: (Parcel) -> T?) =
        object : Parcelable.Creator<T> {
            override fun createFromParcel(source: Parcel): T? = createFromParcel(source)
            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }
