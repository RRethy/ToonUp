package com.bonnetrouge.toonup.Commons.Ext

import android.view.View

fun View.visibilityGone() {
    this.visibility = View.GONE
}

fun View.visibilityInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.visibilityVisible() {
    this.visibility = View.VISIBLE
}
