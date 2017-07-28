package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import io.reactivex.Observable
import javax.inject.Inject

class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {

	val popularCartoons by lazy {
		videoRepository.getPopularCartoons()
	}
}