package com.bonnetrouge.toonup.DI.Modules

import com.bonnetrouge.toonup.Commons.Scopes.ActivityScope
import com.bonnetrouge.toonup.Fragments.BrowsePopularFragment
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import dagger.Module
import dagger.Provides

@Module class BrowseActivityModule