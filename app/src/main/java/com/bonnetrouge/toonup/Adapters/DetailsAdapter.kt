package com.bonnetrouge.toonup.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bonnetrouge.toonup.Activities.DetailActivity
import com.bonnetrouge.toonup.Commons.bindView
import com.bonnetrouge.toonup.Model.DetailSeriesInfo
import com.bonnetrouge.toonup.Model.Episode
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes
import java.lang.ref.WeakReference

class DetailsAdapter(detailActivity: DetailActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val detailActivityWeakRef = WeakReference<DetailActivity>(detailActivity)
    val items = mutableListOf<RVItem>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            RVItemViewTypes.DETAIL_SERIES_INFO -> return DetailInfoViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.view_holder_detail_info, parent, false))
            RVItemViewTypes.EPISODE -> return DetailEpisodeViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.view_holder_detail_episode, parent, false))
        }
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            RVItemViewTypes.DETAIL_SERIES_INFO -> (holder as DetailInfoViewHolder).bind(items[position] as DetailSeriesInfo)
            RVItemViewTypes.EPISODE -> (holder as DetailEpisodeViewHolder).bind(items[position] as Episode)
        }
    }

    override fun getItemViewType(position: Int) = items[position].getItemViewType()

    override fun getItemCount() = items.size

    inner class DetailInfoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val seriesTitle by bindView<TextView>(R.id.seriesTitle)
        val additionInfo by bindView<TextView>(R.id.additionInfo)
        val genres by bindView<TextView>(R.id.genres)
        val description by bindView<TextView>(R.id.seriesDescription)

        fun bind(info: DetailSeriesInfo) {
            seriesTitle.text = info.title
            additionInfo.text = info.additionalInfo
            genres.text = info.genres
            description.text = info.description
        }
    }
    
    inner class DetailEpisodeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val playButton by bindView<ImageView>(R.id.playButton)
        val episodeDate by bindView<TextView>(R.id.episodeDate)
        val episodeName by bindView<TextView>(R.id.episodeName)

        init {
            view.setOnClickListener {
                detailActivityWeakRef.get()?.onRecyclerViewItemClicked(items[adapterPosition])
            }
        }

        fun bind(episode: Episode) {
            episodeDate.text = episode.date
            episodeName.text = episode.name
        }
    }
}