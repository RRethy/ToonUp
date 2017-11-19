package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Data.VideoRepositoryImpl
import com.bonnetrouge.toonup.Model.DescriptiveStreamingUrl
import io.reactivex.Observable
import javax.inject.Inject

class PlayerViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

    var fullStreamingUrls: List<List<DescriptiveStreamingUrl>>? = null
    var rawStreamingUrls: List<List<String>>? = null

    fun getFullStreamingUrls(episodeId: String): Observable<List<List<DescriptiveStreamingUrl>>> {
        return if (fullStreamingUrls != null) Observable.just(fullStreamingUrls)
        else videoRepository.getDescriptiveStreamingUrls(episodeId).doOnNext { fullStreamingUrls = it }
    }

    fun getRawStreamingUrls(episodeId: String): Observable<List<List<String>>> {
        return if (rawStreamingUrls != null) Observable.just(rawStreamingUrls)
        else videoRepository.getRawStreamingUrls(episodeId).doOnNext { rawStreamingUrls = it }
    }
}
