package com.bonnetrouge.toonup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bonnetrouge.toonup.Commons.app

class MainActivity : AppCompatActivity() {

    val component by lazy { app.component }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
