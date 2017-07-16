package com.bonnetrouge.toonup.Components

import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Commons.Scopes.ActivityScope
import com.bonnetrouge.toonup.Modules.BrowseModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(BrowseModule::class))
interface BrowseSubcomponent {
    fun inject(activity: BrowseActivity)
}