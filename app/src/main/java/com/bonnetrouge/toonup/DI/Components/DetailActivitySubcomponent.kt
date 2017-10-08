package com.bonnetrouge.toonup.DI.Components

import com.bonnetrouge.toonup.Activities.DetailActivity
import com.bonnetrouge.toonup.Commons.Scopes.ActivityScope
import com.bonnetrouge.toonup.DI.Modules.DetailActivityModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(DetailActivityModule::class))
interface DetailActivitySubcomponent {
    fun inject(activity: DetailActivity)
}