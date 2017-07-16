package com.bonnetrouge.toonup.Activities

import android.os.Bundle
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Modules.BrowseModule
import com.bonnetrouge.toonup.R

class BrowseActivity: BaseActivity() {

    val component by lazy { app.component.plus(BrowseModule()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
    }
}
