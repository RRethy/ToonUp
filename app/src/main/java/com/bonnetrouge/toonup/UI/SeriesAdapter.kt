package com.bonnetrouge.toonup.UI

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.ListItem
import com.bonnetrouge.toonup.Model.ListItemTypes
import com.bonnetrouge.toonup.R
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class SeriesAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private val contextWeakRef = WeakReference<Context>(context)
	val itemList = ArrayList<ListItem>()
	val thumbnailWidthPx by lazy {
		((getDisplayWidth() - convertToPixels(24.0)) / 3.0).toInt()
	}
	val thumbnailHeightPx by lazy {
		(thumbnailWidthPx * 16.0 / 9.0).toInt()
	}

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
		if (viewType == ListItemTypes.LOADING) {
			return LoadingViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_loading, parent, false))
		} else if (viewType == ListItemTypes.BASIC_SERIES_INFO) {
			return SeriesThumbnailViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_thumbnail, parent, false))
		}
		return null
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
		if (holder is SeriesThumbnailViewHolder) {
			holder.bind(itemList[position])
		}
	}

	override fun getItemCount() = itemList.size

	override fun getItemViewType(position: Int) = itemList[position].getDataType()

	inner class SeriesThumbnailViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

		val thumbnail by lazy { view.findViewById(R.id.thumbnail) as ImageView}

		init {
			thumbnail.layoutParams.width = thumbnailWidthPx
			thumbnail.layoutParams.height = thumbnailHeightPx
		}

		fun bind(listItem: ListItem) {
			val basicSeriesInfo = listItem as BasicSeriesInfo
			Picasso.with(contextWeakRef.get())
					.load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
					.resize(thumbnailWidthPx, thumbnailHeightPx)
					.into(thumbnail)
		}
	}

	inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		init {
			view.setOnTouchListener { v, event ->
				if (event.actionMasked == MotionEvent.ACTION_DOWN)
					Toast.makeText(contextWeakRef.get(), "Don't worry, just let it finish loading", Toast.LENGTH_LONG).show()
				false
			}
		}
	}
}