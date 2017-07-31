package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.Series
import javax.inject.Inject

// TODO: Change pattern to always return an observable
class DetailViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

	var details: Series? = null

	fun getDetailsObservable(id: String) = videoRepository.getDetails(id)
}