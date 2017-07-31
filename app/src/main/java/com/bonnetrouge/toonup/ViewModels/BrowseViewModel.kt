package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// TODO: Change pattern to always return an observable
class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {

	var popularCartoons: Collection<BasicSeriesInfo>? = null

	var popularMovies: Collection<BasicSeriesInfo>? = null

	fun getPopularCartoonObservable() = videoRepository.getPopularCartoons().doOnSuccess { popularCartoons = it }

	fun getPopularMoviesObservable() = videoRepository.getPopularMovies().doOnSuccess { popularMovies = it }
}