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

class DetailsAdapter(detailActivity: DetailActivity) : RecyclerView.Adapter<DetailsAdapter.DetailItemViewHolder>() {

	private val detailActivityWeakRef = WeakReference<DetailActivity>(detailActivity)
	val itemList = ArrayList<Episode>()

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
		= DetailItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_holder_detail, parent, false))

	override fun onBindViewHolder(holder: DetailItemViewHolder, position: Int) = holder.bind(itemList[position])

	override fun getItemCount() = itemList.size

	inner class DetailItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

		val title by lazy { view.findViewById(R.id.detail_item_title) as TextView }

		init {
			//title.setOnClickListener { detailActivityWeakRef.get()?.onRecyclerViewItemClicked(itemList[adapterPosition]) }
		}

		fun bind(episode: Episode) {
			title.text = episode.name
		}
	}
}