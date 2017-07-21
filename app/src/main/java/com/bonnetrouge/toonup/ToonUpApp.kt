package com.bonnetrouge.toonup

import android.app.Application
import com.bonnetrouge.toonup.API.StreamingApiService
import com.bonnetrouge.toonup.DI.Components.DaggerToonUpAppComponent
import com.bonnetrouge.toonup.DI.Components.ToonUpAppComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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