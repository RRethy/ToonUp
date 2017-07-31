package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {

	private var popularCartoons: Collection<BasicSeriesInfo>? = null

	private var popularMovies: Collection<BasicSeriesInfo>? = null

	fun getPopularCartoonObservable(): Single<Collection<BasicSeriesInfo>> {
		if (popularCartoons != null) return Single.just(popularCartoons)
		else return videoRepository.getPopularCartoons().doOnSuccess { popularCartoons = it }
	}

	fun getPopularMoviesObservable(): Single<Collection<BasicSeriesInfo>> {
		if (popularMovies != null) return Single.just(popularMovies)
		else return videoRepository.getPopularMovies().doOnSuccess { popularMovies = it }
	}
}