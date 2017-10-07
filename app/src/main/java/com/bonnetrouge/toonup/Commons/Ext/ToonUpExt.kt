package com.bonnetrouge.toonup.Commons.Ext

import android.util.Log
import com.bonnetrouge.toonup.ToonUpApp

val app: ToonUpApp
    get() = ToonUpApp.app

val DTAG = "quman"

fun dog(text: String) {
    Log.d(DTAG, text)
}
