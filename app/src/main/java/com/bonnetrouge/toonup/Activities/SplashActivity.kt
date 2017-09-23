package com.bonnetrouge.toonup.Activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BrowseActivity.navigate(this)
        finish()
    }
}
