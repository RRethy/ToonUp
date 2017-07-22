package com.bonnetrouge.toonup.UI

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ViewTypeDelegateAdapter {
	fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

	fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: RVViewType)
}