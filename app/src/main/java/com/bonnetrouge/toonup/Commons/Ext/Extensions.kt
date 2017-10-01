package com.bonnetrouge.toonup.Commons.Ext

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


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

fun EditText.showKeyboard() {
    this.requestFocus()
    val imm = app.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun <T> lazyAndroid(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
