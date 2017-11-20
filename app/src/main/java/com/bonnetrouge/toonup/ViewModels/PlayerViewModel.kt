package com.bonnetrouge.toonup.ViewModels

import android.arch.lifecycle.ViewModel
import com.bonnetrouge.toonup.Commons.Ext.getNext
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
    lateinit var currentId: String
    lateinit var idList: List<String>

    fun getFullStreamingUrls(episodeId: String): Observable<List<List<DescriptiveStreamingUrl>>> {
        currentId = episodeId
        return videoRepository.getDescriptiveStreamingUrls(episodeId).doOnNext { fullStreamingUrls = it }
    }

    fun getRawStreamingUrls(episodeId: String): Observable<List<List<String>>> {
        currentId = episodeId
        return videoRepository.getRawStreamingUrls(episodeId).doOnNext { rawStreamingUrls = it }
    }

    fun getRVLinks(): MutableList<RVItem> {
        val items = mutableListOf<RVItem>()
        fullStreamingUrls?.forEachIndexed { index, list ->
            if (fullStreamingUrls!!.size > 1) items.add(PartTitleModelHolder("Part ${index + 1}"))
            list.forEachIndexed { i, descriptiveStreamingUrl ->
                items.add(LinkModelHolder("Link ${i + 1}", descriptiveStreamingUrl.link))
            }
        }
        return items
    }

    fun getNextId(): String =
            with (idList.getNext(currentId)) {
                if (this == currentId) {
                    return ""
                } else {
                    return this
                }
            }

    fun isMultiPartMedia() = fullStreamingUrls!!.size > 1

    fun cacheSeriesIds(ids: String) {
        val trimmedIds = ids.trimEnd { it == '[' || it == ']' }
        idList = trimmedIds.split(", ")
    }
}
