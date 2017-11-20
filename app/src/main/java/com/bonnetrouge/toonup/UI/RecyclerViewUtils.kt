package com.bonnetrouge.toonup.UI

interface RVItem {
    fun getItemViewType(): Int
}

object RVItemViewTypes {
    val INVALID_ITEM = -1
    val LOADING = 0
    val BANNER = 1
    val BASIC_SERIES = 2
    val EPISODE = 3
    val PAGINATION = 4
    val EXTENDED_EPISODE = 5
    val DETAIL_SERIES_INFO = 6
    val DETAIL_EPISODE = 7
    val LINK = 8
    val PART_TITLE = 9
}