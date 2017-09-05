package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.VideoGenres
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {

	private var allCartoons: List<BasicSeriesInfo>? = null
	private var allMovies: List<BasicSeriesInfo>? = null
	var genres: VideoGenres? = null

	fun getAllCartoonsObservable(): Observable<List<BasicSeriesInfo>> {
		return if (allCartoons != null) Observable.just(allCartoons)
		else videoRepository.getAllCartoons().doOnNext { allCartoons = it }
	}

	fun getAllMovies(): Observable<List<BasicSeriesInfo>> {
		return if (allMovies != null) Observable.just(allMovies)
		else videoRepository.getAllMovies().doOnNext { allMovies = it }
	}

	fun ensureGenresNotNull(onSuccess: (VideoGenres) -> Unit, onFailure: () -> Unit) {
		if (genres != null) onSuccess(genres!!)
		else videoRepository.getGenres()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retry(3)
				.subscribe({
					genres = it
					onSuccess(it)
				}, {
					onFailure()
				})
	}

	fun prefetchGenres() {
		if (genres == null) videoRepository.getGenres()
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.subscribe({ genres = it }, { /*Do nothing*/ })
	}
}