package com.bonnetrouge.toonup.DI.Modules

import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import dagger.Module
import dagger.Provides

@Module class BrowseModule {

    @Provides
    fun provideBrowseViewModelFactory(browseViewModel: BrowseViewModel) = BrowseViewModelFactory(browseViewModel)
}