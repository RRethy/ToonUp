package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.resString
import com.bonnetrouge.toonup.Commons.Ext.with
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.VideoGenres
import com.bonnetrouge.toonup.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern
import javax.inject.Inject

class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository): ViewModel() {

	private var cartoons: MutableList<BannerModel>? = null
	private var rawCartoons: MutableList<BasicSeriesInfo>? = null
	private var movies: MutableList<BannerModel>? = null
	private var rawMovies: MutableList<BasicSeriesInfo>? = null
    private var animes: MutableList<BannerModel>? = null
	private var rawAnime: MutableList<BasicSeriesInfo>? = null

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

    fun getCartoonSearchResults(s: CharSequence): MutableList<BasicSeriesInfo> {
		return if (s.isEmpty()) {
			getPopularCartoonsRaw()
		} else {
			filterForCartoons(s)
		}
	}

	private fun getPopularCartoonsRaw(): MutableList<BasicSeriesInfo> {
		val rawCartoons = mutableListOf<BasicSeriesInfo>()
		cartoons?.forEach {
			if (it.title == resString(R.string.popular)) {
				it.dataList.forEach { rawCartoons.add(it as BasicSeriesInfo) }
			}
		}
		return rawCartoons
	}

	private fun filterForCartoons(s: CharSequence): MutableList<BasicSeriesInfo> {
		val lazySearchRegexBuilder = StringBuilder()
		lazySearchRegexBuilder.append("^(")
		s.forEach {
			lazySearchRegexBuilder.append(it)
			lazySearchRegexBuilder.append(".*")
		}
		lazySearchRegexBuilder.append(")")
		val lazySearchPattern = Pattern.compile(lazySearchRegexBuilder.toString(), Pattern.CASE_INSENSITIVE)
		val filteredRawCartoons = mutableListOf<BasicSeriesInfo>()
        rawCartoons?.forEach {
			val lazySearchMatcher = lazySearchPattern.matcher(it.name)
			if (lazySearchMatcher.find()) {
				filteredRawCartoons.add(it)
			}
		}
		return filteredRawCartoons
	}

	private fun getAllCartoons(videoGenres: VideoGenres) =
			videoRepository.getAllCartoons()
					.retry(3)
					.doOnNext { rawCartoons = it.toMutableList() }
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

	private fun getPopularCartoons() =
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

	fun getMoviesSearchResults(s: CharSequence): MutableList<BasicSeriesInfo> {
		return if (s.isEmpty()) {
			getPopularMoviesRaw()
		} else {
			filterForMovies(s)
		}
	}

	fun getPopularMoviesRaw(): MutableList<BasicSeriesInfo> {
		val rawMovies = mutableListOf<BasicSeriesInfo>()
		movies?.forEach {
			if (it.title == resString(R.string.popular)) {
				it.dataList.forEach { rawMovies.add(it as BasicSeriesInfo) }
			}
		}
		return rawMovies
	}

	fun filterForMovies(s: CharSequence): MutableList<BasicSeriesInfo> {
		val lazySearchRegexBuilder = StringBuilder()
		lazySearchRegexBuilder.append("^(")
		s.forEach {
			lazySearchRegexBuilder.append(it)
			lazySearchRegexBuilder.append(".*")
		}
		lazySearchRegexBuilder.append(")")
		val lazySearchPattern = Pattern.compile(lazySearchRegexBuilder.toString(), Pattern.CASE_INSENSITIVE)
		val filteredRawMovies = mutableListOf<BasicSeriesInfo>()
		rawMovies?.forEach {
			val lazySearchMatcher = lazySearchPattern.matcher(it.name)
			if (lazySearchMatcher.find()) {
				filteredRawMovies.add(it)
			}
		}
		return filteredRawMovies
	}

	private fun getAllMovies(videoGenres: VideoGenres) =
			videoRepository.getAllMovies()
					.retry(3)
					.doOnNext { rawMovies = it.toMutableList() }
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

	private fun getPopularMovies() =
			videoRepository.getPopularMovies()
					.retry(3)
					.map {
						BannerModel(resString(R.string.popular), it)
					}
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
	//endregion

    //region Anime
	fun fetchAnime(subscription: Observable<MutableList<BannerModel>>.() -> Unit, error: () -> Unit) {
		ensureGenresNotNull({
			if (animes != null) {
				Observable.just(animes!!).subscription()
			} else {
				val observable = Observable.zip<MutableList<BannerModel>, BannerModel, MutableList<BannerModel>>(
						getAllAnime(it),
						getPopularAnime(),
						BiFunction { allAnime, popularAnime ->
							val list = mutableListOf<BannerModel>()
							list.add(popularAnime)
							list.addAll(allAnime)
							list
						}
				).doOnNext { animes = it }
				observable.subscription()
			}
		}, {
			error()
		})
	}

	fun getAnimeSearchResult(s: CharSequence): MutableList<BasicSeriesInfo> {
		return if (s.isEmpty()) {
            getPopularAnimeRaw()
		} else {
			filterForAnime(s)
		}
	}

	private fun getPopularAnimeRaw(): MutableList<BasicSeriesInfo> {
		val rawAnime = mutableListOf<BasicSeriesInfo>()
		animes?.forEach {
			if (it.title == resString(R.string.popular)) {
				it.dataList.forEach { rawAnime.add(it as BasicSeriesInfo) }
			}
		}
		return rawAnime
	}

	private fun filterForAnime(s: CharSequence): MutableList<BasicSeriesInfo> {
		val lazySearchRegexBuilder = StringBuilder()
		lazySearchRegexBuilder.append("^(")
		s.forEach {
			lazySearchRegexBuilder.append(it)
			lazySearchRegexBuilder.append(".*")
		}
		lazySearchRegexBuilder.append(")")
		val lazySearchPattern = Pattern.compile(lazySearchRegexBuilder.toString(), Pattern.CASE_INSENSITIVE)
		val filteredRawAnime = mutableListOf<BasicSeriesInfo>()
		rawAnime?.forEach {
			val lazySearchMatcher = lazySearchPattern.matcher(it.name)
			if (lazySearchMatcher.find()) {
				filteredRawAnime.add(it)
			}
		}
		return filteredRawAnime
	}

	private fun getAllAnime(videoGenres: VideoGenres) =
			videoRepository.getAllAnime()
					.retry(3)
					.doOnNext { rawAnime = it.toMutableList() }
					.map {
						val animeByGenre = mutableListOf<BannerModel>()
						for (videoGenre in videoGenres.genres) {
							val animeList = it.filter({ it.genres.contains(videoGenre) }).toMutableList()
							animeList.sortByDescending { it.rating }
							if (animeList.size > 0) animeByGenre.add(BannerModel(videoGenre, animeList))
						}
						animeByGenre
					}
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())

	private fun getPopularAnime() =
			videoRepository.getPopularAnime()
					.retry(3)
					.map {
						BannerModel(resString(R.string.popular), it)
					}
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
	//endregion

	private fun ensureGenresNotNull(onSuccess: (VideoGenres) -> Unit, onFailure: () -> Unit) {
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