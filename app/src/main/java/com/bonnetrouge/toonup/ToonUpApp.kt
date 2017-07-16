package com.bonnetrouge.toonup

import android.app.Application
import com.bonnetrouge.toonup.DI.Components.DaggerToonUpAppComponent
import com.bonnetrouge.toonup.DI.Components.ToonUpAppComponent

class ToonUpApp: Application() {

    companion object {
        lateinit var app: ToonUpApp
            private set
    }

    val component: ToonUpAppComponent by lazy {
        DaggerToonUpAppComponent.create()
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        component.inject(this)
    }
}