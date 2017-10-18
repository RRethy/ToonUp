package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.fuzzyFilter
import com.bonnetrouge.toonup.Commons.Ext.resString
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Delegates.*
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.VideoGenres
import com.bonnetrouge.toonup.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BrowseViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

    val dataHolder = BrowseVMDataHolder()

    fun getFormattedMedia(delegate: VMDelegate,
                          showLoading: () -> Unit,
                          subscription: Observable<MutableList<BannerModel>>.() -> Unit,
                          error: () -> Unit) {
        if (delegate.isDataEmpty(dataHolder)) {
            showLoading()
        }
        ensureGenresNotNull({
            if (!delegate.isDataEmpty(dataHolder)) {
                Observable.just(delegate.formattedData(dataHolder)!!).subscription()
            } else {
                val observable = Observable.zip<MutableList<BannerModel>, BannerModel, MutableList<BannerModel>>(
                        requestAllMedia(delegate.requestAllMedia(videoRepository), delegate, it),
                        requestPopularMedia(delegate.requestPopularMedia(videoRepository)),
                        BiFunction { allMedia, popularMedia ->
                            val list = mutableListOf<BannerModel>()
                            list.add(popularMedia)
                            list.addAll(allMedia)
                            list
                        }
                ).doOnNext { dataHolder.cacheFormattedData(delegate, it) }
                observable.subscription()
            }
        }, { error() } )
    }

    fun getSearchResults(delegate: VMDelegate, s: CharSequence): MutableList<BasicSeriesInfo>? {
        return if (s.isEmpty()) {
            getRawPopularMedia(delegate)
        } else {
            filterMedia(delegate, s)
        }
    }

    private fun getRawPopularMedia(delegate: VMDelegate): MutableList<BasicSeriesInfo> {
        val rawPopularMedia = mutableListOf<BasicSeriesInfo>()
        delegate.formattedData(dataHolder)?.forEach {
            if (it.title == resString(R.string.popular)) {
                it.dataList.forEach { rawPopularMedia.add(it as BasicSeriesInfo) }
            }
        }
        return rawPopularMedia
    }

    private fun filterMedia(delegate: VMDelegate, s: CharSequence) =
            delegate.rawData(dataHolder)?.fuzzyFilter(match = s){ this.name }?.toMutableList()

    private fun requestAllMedia(observable: Observable<List<BasicSeriesInfo>>,
                                delegate: VMDelegate,
                                videoGenres: VideoGenres) =
            observable
                    .retry(3)
                    .doOnNext { dataHolder.cacheRawData(delegate, it.toMutableList()) }
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

    private fun requestPopularMedia(observable: Observable<List<BasicSeriesInfo>>) =
            observable
                    .retry(3)
                    .map {
                        BannerModel(resString(R.string.popular), it)
                    }
                    .subscribeOn(Schedulers.io())
    //endregion

    private fun ensureGenresNotNull(onSuccess: (VideoGenres) -> Unit, onFailure: () -> Unit) {
        if (dataHolder.genres != null) onSuccess(dataHolder.genres!!)
        else videoRepository.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(3)
                .subscribe({
                    dataHolder.genres = it
                    onSuccess(it)
                }, {
                    onFailure()
                })
    }

    fun prefetchGenres() {
        if (dataHolder.genres == null) videoRepository.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ dataHolder.genres = it }, { dog("Prefetch Genres failed!") })
    }
}

class BrowseVMDataHolder {

    var cartoons: MutableList<BannerModel>? = null
    var rawCartoons: MutableList<BasicSeriesInfo>? = null
    var movies: MutableList<BannerModel>? = null
    var rawMovies: MutableList<BasicSeriesInfo>? = null
    var anime: MutableList<BannerModel>? = null
    var rawAnime: MutableList<BasicSeriesInfo>? = null

    var genres: VideoGenres? = null

    fun cacheFormattedData(delegate: VMDelegate, data: MutableList<BannerModel>) {
        when (delegate) {
            is CartoonsVMDelegate -> cartoons = data
            is MoviesVMDelegate -> movies = data
            is AnimeVMDelegate -> anime = data
        }
    }

    fun cacheRawData(delegate: VMDelegate, data: MutableList<BasicSeriesInfo>) {
        when (delegate) {
            is CartoonsVMDelegate -> rawCartoons = data
            is MoviesVMDelegate -> rawMovies = data
            is AnimeVMDelegate -> rawAnime = data
        }
    }
}
