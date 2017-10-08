package com.bonnetrouge.toonup.ViewModels.ViewModelFactories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import javax.inject.Inject


class BrowseViewModelFactory @Inject constructor(val browseViewModel: BrowseViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        modelClass?.let {
            if (modelClass.isAssignableFrom(BrowseViewModel::class.java)) {
                return browseViewModel as? T ?: throw IllegalArgumentException("Unknown model class type!")
            }

        }
        throw IllegalArgumentException("Null model class!")
    }
}