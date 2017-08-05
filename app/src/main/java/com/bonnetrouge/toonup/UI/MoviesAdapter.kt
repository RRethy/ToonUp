package com.bonnetrouge.toonup.UI

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth
import com.bonnetrouge.toonup.Fragments.BrowseMoviesFragment
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.lang.ref.WeakReference

class MoviesAdapter(moviesFragment: BrowseMoviesFragment) : RecyclerView.Adapter<MoviesAdapter.MovieThumbnailViewHolder>() {

	private val browseMoviesWeakRef = WeakReference<BrowseMoviesFragment>(moviesFragment)
	val itemList = ArrayList<BasicSeriesInfo>()
	val thumbnailWidthPx by lazy { ((getDisplayWidth() - convertToPixels(24.0)) / 3.0).toInt() }
	val thumbnailHeightPx by lazy { (thumbnailWidthPx * 16.0 / 9.0).toInt() }

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
		= MovieThumbnailViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_thumbnail, parent, false))


	override fun onBindViewHolder(holder: MovieThumbnailViewHolder, position: Int) = holder.bind(itemList[position])

	override fun getItemCount() = itemList.size

	inner class MovieThumbnailViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

		val thumbnail by lazy { view.findViewById(R.id.thumbnail) as ImageView }

		init {
			thumbnail.layoutParams.width = thumbnailWidthPx
			thumbnail.layoutParams.height = thumbnailHeightPx
			view.setOnClickListener {
				browseMoviesWeakRef.get()?.onRecyclerViewItemClicked(itemList[adapterPosition])
			}
		}

		fun bind(basicSeriesInfo: BasicSeriesInfo) {
			Glide.with(browseMoviesWeakRef.get())
					.load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
					.apply(RequestOptions().override(thumbnailWidthPx, thumbnailHeightPx).fitCenter())
					.into(thumbnail)
		}
	}
}