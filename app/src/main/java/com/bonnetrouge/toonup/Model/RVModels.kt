package com.bonnetrouge.toonup.Model

import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes

class Loading : RVItem {
    override fun getItemViewType() = RVItemViewTypes.LOADING
}

data class BannerModel(val title: String, val dataList: List<RVItem>) : RVItem {
    override fun getItemViewType() = RVItemViewTypes.BANNER
}

data class DetailSeriesInfo(var title: String = "",
                            var additionalInfo: String = "",
                            var genres: String = "",
                            var description: String = "") : RVItem {
    override fun getItemViewType() = RVItemViewTypes.DETAIL_SERIES_INFO
}

data class LinkModelHolder(val linkTitle: String = "", val link: String = "") : RVItem {
    override fun getItemViewType() = RVItemViewTypes.LINK
}

data class PartTitleModelHolder(val partTitle: String = "") : RVItem {
    override fun getItemViewType() = RVItemViewTypes.PART_TITLE
}
