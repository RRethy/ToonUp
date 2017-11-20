package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.Model.DescriptiveStreamingUrl
import com.bonnetrouge.toonup.Model.LinkModelHolder
import com.bonnetrouge.toonup.Model.PartTitleModelHolder
import com.bonnetrouge.toonup.UI.RVItem
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

    fun getRVLinks(): MutableList<RVItem> {
        val items = mutableListOf<RVItem>()
        fullStreamingUrls?.forEachIndexed { index, list ->
            if (fullStreamingUrls!!.size > 1) items.add(PartTitleModelHolder("Part $index"))
            list.forEachIndexed { i, descriptiveStreamingUrl ->
                items.add(LinkModelHolder("Link $i", descriptiveStreamingUrl.link))
            }
        }
        return items
    }

    fun isMultiPartMedia() = fullStreamingUrls!!.size > 1
}
