package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.FullSeriesInfo
import com.bonnetrouge.toonup.Model.Series
import io.reactivex.Observable
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

    var basicSeriesDetails: Series? = null
    private var fullSeriesInfo: FullSeriesInfo? = null

    fun getBasicDetails(seriesId: String): Observable<Series> {
        return if (basicSeriesDetails != null) Observable.just(basicSeriesDetails)
        else return videoRepository.getDetails(seriesId).doOnNext { basicSeriesDetails = it }
    }

    fun getFullSeriesInfo(seriesName: String): Observable<FullSeriesInfo> {
        return if (fullSeriesInfo != null) Observable.just(fullSeriesInfo)
        else return videoRepository.getExtendedEpisodesInfo(seriesName).doOnNext { fullSeriesInfo = it }
    }
}