package com.bonnetrouge.toonup.UI

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class GeneralAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private val items = ArrayList<RVViewType>()
	private val delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
	private val loadingItem = object : RVViewType {
		override fun getViewType() = AdapterConstants.LOADING_ITEM
	}
	private val popularItem = object : RVViewType {
		override fun getViewType() = AdapterConstants.POPULAR_ITEM
	}

	init {
		delegateAdapters.put(AdapterConstants.LOADING_ITEM, LoadingItemDelegateAdapter())
		delegateAdapters.put(AdapterConstants.POPULAR_ITEM, PopularItemDelegateAdapter())
		items.add(loadingItem)
		items.add(popularItem)
	}

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun getItemCount(): Int {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}