package com.bonnetrouge.toonup.Model

import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes

data class BannerModel(val title: String, val dataList: List<RVItem>) : RVItem {
    override fun getItemViewType() = RVItemViewTypes.BANNER
}
