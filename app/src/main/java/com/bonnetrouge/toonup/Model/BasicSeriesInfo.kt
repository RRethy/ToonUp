package com.bonnetrouge.toonup.Model

import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes

data class BasicSeriesInfo(val id: String,
						   val name: String,
						   val description: String,
						   val status: String,
						   val released: String,
						   val rating: Float,
						   val genres: Collection<String>) : RVItem {
	override fun getItemViewType() = RVItemViewTypes.BASIC_SERIES_ITEM
}