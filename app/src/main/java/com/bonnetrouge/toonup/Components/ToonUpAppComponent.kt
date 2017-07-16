package com.bonnetrouge.toonup.Components

import com.bonnetrouge.toonup.Modules.ToonUpAppModule
import com.bonnetrouge.toonup.ToonUpApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ToonUpAppModule::class))
interface ToonUpAppComponent {
    fun inject(app: ToonUpApp)
}