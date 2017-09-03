package com.bonnetrouge.toonup.UI

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bonnetrouge.toonup.Activities.DetailActivity
import com.bonnetrouge.toonup.Model.Episode
import com.bonnetrouge.toonup.R
import java.lang.ref.WeakReference

class DetailsAdapter(detailActivity: DetailActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private val detailActivityWeakRef = WeakReference<DetailActivity>(detailActivity)
	val items = mutableListOf<RVItem>()

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
		when (viewType) {
			RVItemViewTypes.EPISODE -> return DetailItemViewHolder(LayoutInflater.from(parent?.context)
					.inflate(R.layout.view_holder_detail, parent, false))
			RVItemViewTypes.LOADING -> return LoadingViewHolder(LayoutInflater.from(parent?.context)
					.inflate(R.layout.view_holder_loading, parent, false))
		}
		return null
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (getItemViewType(position)) {
			RVItemViewTypes.EPISODE -> (holder as DetailItemViewHolder).bind(items[position] as Episode)
		}
	}

	override fun getItemViewType(position: Int) = items[position].getItemViewType()

	override fun getItemCount() = items.size

	inner class DetailItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

		val title by lazy { view.findViewById(R.id.detail_item_title) as TextView }

		init {
			//title.setOnClickListener { detailActivityWeakRef.get()?.onRecyclerViewItemClicked(items[adapterPosition]) }
		}

		fun bind(episode: Episode) {
			title.text = episode.name
		}
	}
}