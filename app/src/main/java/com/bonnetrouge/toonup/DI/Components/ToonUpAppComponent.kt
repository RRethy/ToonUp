package com.bonnetrouge.toonup.DI.Components

import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.DI.Modules.ToonUpAppModule
import com.bonnetrouge.toonup.ToonUpApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ToonUpAppModule::class))
interface ToonUpAppComponent {
    fun inject(app: ToonUpApp)
    fun plus(browseActivityModule: BrowseActivityModule): BrowseActivitySubcomponent
}