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
		if (fullStreamingUrls != null) return Observable.just(fullStreamingUrls)
		else return videoRepository.getFullStreamingUrls(episodeId).doOnNext { fullStreamingUrls = it }
	}

	fun getRawStreamingUrls(episodeId: String): Observable<List<List<String>>> {
		if (rawStreamingUrls != null) return Observable.just(rawStreamingUrls)
		else return videoRepository.getRawStreamingUrls(episodeId).doOnNext { rawStreamingUrls = it }
	}
}
