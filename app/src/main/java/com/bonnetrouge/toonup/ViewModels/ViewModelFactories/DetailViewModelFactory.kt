package com.bonnetrouge.toonup.ViewModels.ViewModelFactories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bonnetrouge.toonup.ViewModels.DetailViewModel
import javax.inject.Inject


class DetailViewModelFactory @Inject constructor(val detailViewModel: DetailViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        modelClass?.let {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return detailViewModel as? T ?: throw IllegalArgumentException("Unknown model class type!")
            }

        }
        throw IllegalArgumentException("Null model class!")
    }
}