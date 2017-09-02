package com.bonnetrouge.toonup.UI

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bumptech.glide.Glide
import java.lang.ref.WeakReference

class BannerItemsAdapter(fragment: BaseFragment?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	private val fragmentWeakRef = WeakReference<BaseFragment>(fragment)
	val items = mutableListOf<RVItem>()

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
		when (viewType) {
			RVItemViewTypes.BASIC_SERIES_ITEM->
				return BannerItemViewHolder(LayoutInflater.from(parent?.context)
						.inflate(R.layout.banner_item, parent, false))
		}
		return null
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (getItemViewType(position)) {
			RVItemViewTypes.BASIC_SERIES_ITEM -> (holder as BannerItemViewHolder).bind(items[position] as BasicSeriesInfo)
		}
	}

	override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
		super.onViewDetachedFromWindow(holder)
		holder?.itemView?.clearAnimation()
	}

	override fun getItemViewType(position: Int) = items[position].getItemViewType()

	override fun getItemCount() = items.size

	inner class BannerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val image = view.findViewById(R.id.bannerItemImage) as ImageView
		val title = view.findViewById(R.id.bannerItemTitle) as TextView

		fun bind(basicSeriesInfo: BasicSeriesInfo) {
			itemView.startAnimation(AnimationUtils.loadAnimation(fragmentWeakRef.get()?.context, R.anim.grow))
			title.text = basicSeriesInfo.name
			Glide.with(fragmentWeakRef.get())
					.load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
					.into(image)
		}
	}
}