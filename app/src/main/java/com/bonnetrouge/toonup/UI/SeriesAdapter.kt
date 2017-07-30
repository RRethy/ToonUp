package com.bonnetrouge.toonup.UI

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class SeriesAdapter(context: Context) : RecyclerView.Adapter<SeriesAdapter.SeriesThumbnailViewHolder>() {

	private val contextWeakRef = WeakReference<Context>(context)
	val itemList = ArrayList<BasicSeriesInfo>()
	val thumbnailWidthPx by lazy {
		((getDisplayWidth() - convertToPixels(24.0)) / 3.0).toInt()
	}
	val thumbnailHeightPx by lazy {
		(thumbnailWidthPx * 16.0 / 9.0).toInt()
	}

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
			= SeriesThumbnailViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_thumbnail, parent, false))


	override fun onBindViewHolder(holder: SeriesThumbnailViewHolder, position: Int) = holder.bind(itemList[position])

	override fun getItemCount() = itemList.size

	inner class SeriesThumbnailViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

		val thumbnail by lazy { view.findViewById(R.id.thumbnail) as ImageView}

		init {
			thumbnail.layoutParams.width = thumbnailWidthPx
			thumbnail.layoutParams.height = thumbnailHeightPx
		}

		fun bind(basicSeriesInfo: BasicSeriesInfo) {
			Picasso.with(contextWeakRef.get())
					.load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
					.resize(thumbnailWidthPx, thumbnailHeightPx)
					.into(thumbnail)
		}
	}
}