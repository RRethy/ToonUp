package com.bonnetrouge.toonup.UI

interface RVItem {
	fun getItemViewType(): Int
}

object RVItemViewTypes {
	val LOADING = 0
	val BANNER = 1
	val BANNER_ITEM = 2
}
