package com.bonnetrouge.toonup.Commons.Ext

inline fun String.notEmpty(action: String.() -> Unit) {
    if (this.isNotEmpty()) this.action()
}

