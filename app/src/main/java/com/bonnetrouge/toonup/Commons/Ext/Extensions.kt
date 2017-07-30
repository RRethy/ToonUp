package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.FragmentTransaction
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity



val app: ToonUpApp
    get() = ToonUpApp.app

inline fun BaseActivity.fragmentTransaction(swapInfo: FragmentTransaction.() -> FragmentTransaction)
        = supportFragmentManager.beginTransaction().swapInfo().commit()

fun convertToPixels(sizeInDp: Double): Int {
    val scale = app.resources.displayMetrics.density
    return (sizeInDp * scale + 0.5f).toInt()
}

fun convertToDp(sizeInPixels: Int): Float {
    val densityDpi = app.resources.displayMetrics.densityDpi
    return sizeInPixels / (densityDpi / 160f)
}

fun getDisplayWidth(): Int {
    return app.resources.displayMetrics.widthPixels
}

fun getDisplayHeight(): Int {
    return app.resources.displayMetrics.heightPixels
}