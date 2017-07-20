package com.bonnetrouge.toonup.UI

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bonnetrouge.toonup.R

class LoadingItemDelegateAdapter : ViewTypeDelegateAdapter {
	override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
		TODO("not implemented")
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: RVViewType) {
		TODO("not implemented")
	}

	class LoadingItemViewHolder(parent: ViewGroup)
		: RecyclerView.ViewHolder(
			LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false)) {

	}
}