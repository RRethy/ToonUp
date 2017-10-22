package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.remove
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.DetailSeriesInfo
import com.bonnetrouge.toonup.Model.Series
import com.bonnetrouge.toonup.UI.RVItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

    var basicSeriesDetails: Series? = null
    lateinit var id: String
    lateinit var title: String
    lateinit var description: String
    lateinit var released: String
    lateinit var genres: String
    lateinit var rating: String
    lateinit var status: String

    fun getMediaInfo(): Observable<MutableList<RVItem>> {
        return if (basicSeriesDetails != null) Observable.just(basicSeriesDetails)
                .map {
                    transformToRVModel(it)
                }
        else return videoRepository.getDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { basicSeriesDetails = it }
                .map {
                    transformToRVModel(it)
                }
    }

    fun transformToRVModel(series: Series): MutableList<RVItem> {
        val items = mutableListOf<RVItem>()
        items.add(DetailSeriesInfo(title,
                "${rating.substring(0, 3)}/10  -  $released",
                genres.remove("[").remove("]"),
                description))
        items.addAll(series.episode.onEach { it.name = it.name.remove("$title ") })
        return items
    }
}