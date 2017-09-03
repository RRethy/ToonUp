package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.Series
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

	private var mediaDetails: Series? = null

	fun getDetails(seriesId: String): Observable<Series> {
		return if (mediaDetails != null) Observable.just(mediaDetails)
		else return videoRepository.getDetails(seriesId)
	}
}