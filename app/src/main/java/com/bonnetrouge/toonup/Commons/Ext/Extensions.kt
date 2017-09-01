package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity



val app: ToonUpApp
    get() = ToonUpApp.app

val DTAG = "quman"

inline fun BaseActivity.fragmentTransaction(tag: String? = null, swapInfo: FragmentTransaction.() -> FragmentTransaction)
        = supportFragmentManager.beginTransaction().swapInfo().addToBackStack(tag).commit()

fun <T> MutableList<T>.shuffle() {
    for (count in 1..4) {
		for (i in 0 until this.size step 2) {
			this.add(this.size, this[i])
			this.removeAt(i)
		}
    }
}

fun convertToPixels(sizeInDp: Double): Int {
    val scale = app.resources.displayMetrics.density
    return (sizeInDp * scale + 0.5f).toInt()
}

fun dog(text: String) {
    Log.d("quman", text)
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