package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.resString
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.VideoGenres
import com.bonnetrouge.toonup.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {

	private var cartoons: MutableList<BannerModel>? = null
	private var movies: MutableList<BannerModel>? = null
    private var animes: MutableList<BannerModel>? = null
	private var allMovies: List<BasicSeriesInfo>? = null
	private var popularMovies: List<BasicSeriesInfo>? = null

	private var allAnime: List<BasicSeriesInfo>? = null
	private var popularAnime: List<BasicSeriesInfo>? = null

	var genres: VideoGenres? = null

	//region Cartoons
	fun fetchCartoons(subscription: Observable<MutableList<BannerModel>>.() -> Unit, error: () -> Unit) {
		ensureGenresNotNull({
            if (cartoons != null) {
                Observable.just(cartoons!!).subscription()
			} else {
				val observable = Observable.zip<MutableList<BannerModel>, BannerModel, MutableList<BannerModel>>(
						getAllCartoons(it),
						getPopularCartoons(),
						BiFunction { allSeries, popularCartoons ->
							val list = mutableListOf<BannerModel>()
							list.add(popularCartoons)
							list.addAll(allSeries)
							list
						}
				).doOnNext { cartoons = it }
				observable.subscription()
			}
		}, {
			error()
		})
	}

	fun getAllCartoons(videoGenres: VideoGenres) =
			videoRepository.getAllCartoons()
					.retry(3)
					.map {
						val seriesByGenre = mutableListOf<BannerModel>()
						for (videoGenre in videoGenres.genres) {
							val seriesList = it.filter({ it.genres.contains(videoGenre) }).toMutableList()
							seriesList.sortByDescending { it.rating }
							if (seriesList.size > 0) seriesByGenre.add(BannerModel(videoGenre, seriesList))
						}
						seriesByGenre
					}
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())

	fun getPopularCartoons() =
			videoRepository.getPopularCartoons()
					.retry(3)
					.map {
						BannerModel(resString(R.string.popular), it)
					}
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
	//endregion

	//region Movies
	fun fetchMovies(subscription: Observable<MutableList<BannerModel>>.() -> Unit, error: () -> Unit) {
		ensureGenresNotNull({
			if (movies != null) {
				Observable.just(movies!!).subscription()
			} else {
				val observable = Observable.zip<MutableList<BannerModel>, BannerModel, MutableList<BannerModel>>(
						getAllMovies(it),
						getPopularMovies(),
						BiFunction { allMovies, popularMovies ->
							val list = mutableListOf<BannerModel>()
							list.add(popularMovies)
							list.addAll(allMovies)
							list
						}
				).doOnNext { movies = it }
				observable.subscription()
			}
		}, {
			error()
		})
	}

	fun getAllMovies(videoGenres: VideoGenres) =
			videoRepository.getAllMovies()
					.retry(3)
					.map {
						val moviesByGenre = mutableListOf<BannerModel>()
						for (videoGenre in videoGenres.genres) {
							val moviesList = it.filter({ it.genres.contains(videoGenre) }).toMutableList()
							moviesList.sortByDescending { it.rating }
							if (moviesList.size > 0) moviesByGenre.add(BannerModel(videoGenre, moviesList))
						}
						moviesByGenre
					}
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())

	fun getPopularMovies() =
			videoRepository.getPopularMovies()
					.retry(3)
					.map {
						BannerModel(resString(R.string.popular), it)
					}
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
	//endregion

    //region Anime
	fun getAllAnime(): Observable<List<BasicSeriesInfo>> {
		return if (allAnime != null) Observable.just(allAnime)
		else videoRepository.getAllAnime().doOnNext { allAnime = it }
	}

	fun getPopularAnime(): Observable<List<BasicSeriesInfo>> {
		return if (popularAnime != null) Observable.just(popularAnime)
		else videoRepository.getPopularAnime().doOnNext { popularAnime = null }
	}
	//endregion

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
				.subscribe({ genres = it }, { dog("Prefetch Genres failed!") })
	}
}