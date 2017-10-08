package com.bonnetrouge.toonup.DI.Components

import com.bonnetrouge.toonup.Activities.PlayerActivity
import com.bonnetrouge.toonup.Commons.Scopes.ActivityScope
import com.bonnetrouge.toonup.DI.Modules.PlayerActivityModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(PlayerActivityModule::class))
interface PlayerActivitySubcomponent {
    fun inject(activity: PlayerActivity)
}
