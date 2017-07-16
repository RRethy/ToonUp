package com.bonnetrouge.toonup.DI.Modules

import com.bonnetrouge.toonup.ToonUpApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ToonUpAppModule(val app: ToonUpApp) {
    @Provides
    @Singleton
    fun provideToonUpApp() = app
}