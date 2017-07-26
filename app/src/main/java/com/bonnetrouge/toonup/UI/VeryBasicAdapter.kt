package com.bonnetrouge.toonup.UI

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class VeryBasicAdapter(context: Context) : RecyclerView.Adapter<VeryBasicAdapter.BasicViewHolder>() {

	val seriesItems = ArrayList<BasicSeriesInfo>()
	val contextWeakReference = WeakReference<Context>(context)

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BasicViewHolder {
		return BasicViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_basic_series, parent, false))
	}

	override fun onBindViewHolder(holder: BasicViewHolder?, position: Int) {
		holder?.bind(seriesItems[position])
	}

	override fun getItemCount() = seriesItems.size

	inner class BasicViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {

		fun bind(basicSeriesInfo: BasicSeriesInfo) {
			Picasso.with(contextWeakReference.get()).load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg").into(rootView.findViewById(R.id.seriesCover) as ImageView)
			(rootView.findViewById(R.id.seriesTitle) as TextView).text = basicSeriesInfo.name
		}
	}
}