package com.bonnetrouge.toonup.UI

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.lang.ref.WeakReference

class BannerItemsAdapter(fragment: BaseFragment?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	private val fragmentWeakRef = WeakReference<BaseFragment>(fragment)
	val items = mutableListOf<RVItem>()

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
		when (viewType) {
			RVItemViewTypes.BANNER_ITEM ->
				return BannerItemViewHolder(LayoutInflater.from(parent?.context)
						.inflate(R.layout.banner_item, parent, false))
		}
		return null
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (getItemViewType(position)) {
			RVItemViewTypes.BANNER_ITEM -> (holder as BannerItemViewHolder).bind(items[position])
		}
	}

	override fun getItemViewType(position: Int) = items[position].getItemViewType()

	override fun getItemCount() = items.size

	inner class BannerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val image = view.findViewById(R.id.bannerItemImage) as ImageView

		//TODO: don't use rvitem
		fun bind(rvItem: RVItem) {
/*			Glide.with(fragmentWeakRef.get())
					.load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
					.apply(RequestOptions().override(thumbnailWidthPx, thumbnailHeightPx).fitCenter())
					.into(image)*/
		}
	}
}