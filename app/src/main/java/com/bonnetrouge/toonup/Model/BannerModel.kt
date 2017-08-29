package com.bonnetrouge.toonup.Model

import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes

data class BannerModel(val dataList: List<RVItem>, val title: String) : RVItem {
	override fun getItemViewType() = RVItemViewTypes.BANNER
}
