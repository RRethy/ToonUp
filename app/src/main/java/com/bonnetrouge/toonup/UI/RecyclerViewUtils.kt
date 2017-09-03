package com.bonnetrouge.toonup.UI

interface RVItem {
	fun getItemViewType(): Int
}

object RVItemViewTypes {
	val LOADING = 0
	val BANNER = 1
	val BASIC_SERIES = 2
	val EPISODE = 3
	val PAGINATION = 4
}
