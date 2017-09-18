package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.DescriptiveStreamingUrl
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class PlayerViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

	private var fullStreamingUrls: List<List<DescriptiveStreamingUrl>>? = null
	private var rawStreamingUrls: List<List<String>>? = null

	fun getFullStreamingUrls(episodeId: String): Observable<List<List<DescriptiveStreamingUrl>>> {
        return if (fullStreamingUrls != null) Observable.just(fullStreamingUrls)
        else videoRepository.getFullStreamingUrls(episodeId).doOnNext { fullStreamingUrls = it }
	}

	fun getRawStreamingUrls(episodeId: String): Observable<List<List<String>>> {
        return if (rawStreamingUrls != null) Observable.just(rawStreamingUrls)
        else videoRepository.getRawStreamingUrls(episodeId).doOnNext { rawStreamingUrls = it }
	}
}
