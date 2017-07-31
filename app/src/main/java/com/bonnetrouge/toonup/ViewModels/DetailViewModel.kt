package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.Series
import io.reactivex.Single
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val videoRepository: VideoRepository) : ViewModel() {

	private var detailsMap: HashMap<String, Series> = HashMap()

	fun getDetailsObservable(id: String): Single<Series> {
		if (detailsMap.containsKey(id)) return Single.just(detailsMap[id])
		else return videoRepository.getDetails(id).doOnSuccess { detailsMap.put(id, it) }
	}
}