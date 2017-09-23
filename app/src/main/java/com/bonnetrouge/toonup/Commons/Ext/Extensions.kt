package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity



val app: ToonUpApp
    get() = ToonUpApp.app

val DTAG = "quman"

inline fun BaseActivity.fragmentTransaction(addToBackStack: Boolean = true, tag: String? = null, swapInfo: FragmentTransaction.() -> FragmentTransaction) {
	if (addToBackStack) supportFragmentManager.beginTransaction().swapInfo().addToBackStack(tag).commit()
	else supportFragmentManager.beginTransaction().swapInfo().commit()
}

inline fun String.notEmpty(action: String.() -> Unit) {
	if (this.isNotEmpty()) this.action()
}

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

fun <T> T?.doElse(notNullAction: () -> Unit, nullAction: () -> Unit) {
    if (this != null) {
        notNullAction()
    } else {
        nullAction()
    }
}

fun <T> withNotNull(variable: T?, action: T.() -> Unit) {
    variable?.let { variable.action() }
}

fun <T> T?.doWith(action: T.() -> Unit) {
    this?.let { this.action() }
}
