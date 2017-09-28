package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity

val app: ToonUpApp
    get() = ToonUpApp.app

val DTAG = "quman"

fun dog(text: String) {
    Log.d("quman", text)
}

inline fun BaseActivity.fragmentTransaction(addToBackStack: Boolean = true, tag: String? = null, swapInfo: FragmentTransaction.() -> FragmentTransaction) {
	if (addToBackStack) supportFragmentManager.beginTransaction().swapInfo().addToBackStack(tag).commit()
	else supportFragmentManager.beginTransaction().swapInfo().commit()
}

inline fun String.notEmpty(action: String.() -> Unit) {
	if (this.isNotEmpty()) this.action()
}

fun convertToPixels(sizeInDp: Double): Int {
    val scale = app.resources.displayMetrics.density
    return (sizeInDp * scale + 0.5f).toInt()
}

fun convertToDp(sizeInPixels: Int): Float {
    val densityDpi = app.resources.displayMetrics.densityDpi
    return sizeInPixels / (densityDpi / 160f)
}

fun getDisplayWidth(): Int = app.resources.displayMetrics.widthPixels

fun getDisplayHeight(): Int = app.resources.displayMetrics.heightPixels

fun <T> T?.notNullElse(notNullAction: () -> Unit, nullAction: () -> Unit) {
    if (this != null) {
        notNullAction()
    } else {
        nullAction()
    }
}

fun <T> withNotNull(variable: T?, action: T.() -> Unit) {
    variable?.let { variable.action() }
}

fun <T> T.with(action: T.() -> Unit) {
    this.action()
}

fun <T> T?.ifNull(action: () -> Unit) {
    if (this == null) action()
}
