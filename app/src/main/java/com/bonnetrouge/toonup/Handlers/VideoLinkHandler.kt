package com.bonnetrouge.toonup.Handlers

import com.bonnetrouge.toonup.Commons.Ext.lazyAndroid
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.PlayerViewModel
import javax.inject.Inject

class VideoLinkHandler @Inject constructor() {

    lateinit var viewModel: PlayerViewModel

    private var part = 0
    private var linkIndex = 0
    private val maxParts by lazyAndroid { viewModel.fullStreamingUrls!!.size }
    private val maxLinks by lazyAndroid { { part: Int -> viewModel.fullStreamingUrls!![part].size} }

    private val getLink = { part: Int, linkIndex: Int -> viewModel.fullStreamingUrls!![part][linkIndex].link }

    private val linkFailureMsg = R.string.bad_link_msg
    private val partEndMsg = R.string.next_part_msg

    lateinit var onNewLink: (String) -> Unit
    lateinit var messageDispatcher: (Int, Int) -> Unit
    lateinit var onAllMediaWatched: () -> Unit
    lateinit var processNextMedia: (String) -> Unit

    private fun moveToNextEpisode() {
        onNewLink("")
    }

    private fun onPartEnded() {
        if (part < (maxParts - 1)) {
            messageDispatcher(partEndMsg, part + 2)
            part++
            linkIndex = 0
            onNewLink(getLink(part, linkIndex))
        } else {
            moveToNextEpisode()
        }
    }

    fun onLinkError() {
        messageDispatcher(linkFailureMsg, linkIndex + 2)
        if (linkIndex < (maxLinks(part) - 1)) {
            linkIndex++
            onNewLink(getLink(part, linkIndex))
        } else {
            linkIndex = 0
            if (part < (maxParts + 1)) {
                onPartEnded()
            } else {
                moveToNextEpisode()
            }
        }
    }

    fun onMediaEnded() {
        if (viewModel.isMultiPartMedia()) {
            onPartEnded()
        } else {
            val nextId = viewModel.getNextId()
            if (nextId == "") {
                onAllMediaWatched()
            } else {
                processNextMedia(nextId)
            }
        }
    }
}
