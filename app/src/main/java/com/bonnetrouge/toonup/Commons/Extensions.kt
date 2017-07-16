package com.bonnetrouge.toonup.Commons

import android.support.v7.app.AppCompatActivity
import com.bonnetrouge.toonup.ToonUpApp

val AppCompatActivity.app: ToonUpApp
    get() = application as ToonUpApp