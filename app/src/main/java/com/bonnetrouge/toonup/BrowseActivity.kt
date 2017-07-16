package com.bonnetrouge.toonup

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bonnetrouge.toonup.Commons.app
import com.bonnetrouge.toonup.Modules.BrowseModule

class BrowseActivity : AppCompatActivity() {

    val component by lazy { app.component.plus(BrowseModule()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
    }
}
