package com.bonnetrouge.toonup.DI.Components

import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Commons.Scopes.ActivityScope
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(BrowseActivityModule::class))
interface BrowseActivitySubcomponent {
    fun inject(activity: BrowseActivity)
}