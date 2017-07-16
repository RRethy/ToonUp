package com.bonnetrouge.toonup

import android.app.Application
import com.bonnetrouge.toonup.Components.DaggerToonUpAppComponent
import com.bonnetrouge.toonup.Components.ToonUpAppComponent

class ToonUpApp: Application() {

    val component: ToonUpAppComponent by lazy {
        DaggerToonUpAppComponent.create()
    }

    companion object {
        lateinit var app: ToonUpApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        component.inject(this)
    }
}