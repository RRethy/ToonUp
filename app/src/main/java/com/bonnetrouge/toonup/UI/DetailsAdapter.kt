package com.bonnetrouge.toonup.UI

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bonnetrouge.toonup.Activities.DetailActivity
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Model.Episode
import com.bonnetrouge.toonup.Model.ExtendedEpisodeInfo
import com.bonnetrouge.toonup.R
import com.bumptech.glide.Glide
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
			RVItemViewTypes.EXTENDED_EPISODE -> return ExtendedDetailItemViewHolder(LayoutInflater.from(parent?.context)
					.inflate(R.layout.view_holder_descriptive_episode, parent, false))
		}
		return null
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (getItemViewType(position)) {
			RVItemViewTypes.EPISODE -> (holder as DetailItemViewHolder).bind(items[position] as Episode)
			RVItemViewTypes.EXTENDED_EPISODE -> (holder as ExtendedDetailItemViewHolder).bind(items[position] as ExtendedEpisodeInfo)
		}
	}

	override fun getItemViewType(position: Int) = items[position].getItemViewType()

	override fun getItemCount() = items.size

	inner class ExtendedDetailItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

		val seasonEpisodeNumber by lazy { view.findViewById(R.id.seasonEpisodeNumber) as TextView }
		val episodeName by lazy { view.findViewById(R.id.episodeName) as TextView }
		val episodeImage by lazy { view.findViewById(R.id.episodeImage) as ImageView }

		init {
			//title.setOnClickListener { detailActivityWeakRef.get()?.onRecyclerViewItemClicked(items[adapterPosition]) }
		}

		fun bind(episode: ExtendedEpisodeInfo) {
			episodeName.text = episode.name
			with (app.applicationContext) {
				seasonEpisodeNumber.text = "${getString(R.string.season)}${episode.season}${getString(R.string.episode)}${episode.number}"
			}
/*			Glide.with(detailActivityWeakRef.get())
					.load(episode.image?.original)
					.into(episodeImage)*/
		}
	}

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