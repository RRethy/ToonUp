package com.bonnetrouge.toonup.ViewModels.ViewModelFactories

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.bonnetrouge.toonup.ViewModels.PlayerViewModel
import javax.inject.Inject

class PlayerViewModelFactory @Inject constructor(val playerViewModel: PlayerViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        modelClass?.let {
            if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
                return playerViewModel as? T ?: throw IllegalArgumentException("Unknown model class type!")
            }

        }
        throw IllegalArgumentException("Null model class!")
    }
}
