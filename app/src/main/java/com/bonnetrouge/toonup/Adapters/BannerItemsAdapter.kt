package com.bonnetrouge.toonup.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bonnetrouge.toonup.Commons.bindView
import com.bonnetrouge.toonup.Fragments.BrowsingFragment
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes
import com.bumptech.glide.Glide
import java.lang.ref.WeakReference

class BannerItemsAdapter(fragment: BrowsingFragment?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val fragmentWeakRef = WeakReference<BrowsingFragment>(fragment)
    val items = mutableListOf<RVItem>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            RVItemViewTypes.BASIC_SERIES ->
                return BannerItemViewHolder(LayoutInflater.from(parent?.context)
                        .inflate(R.layout.banner_item, parent, false))
        }
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            RVItemViewTypes.BASIC_SERIES -> (holder as BannerItemViewHolder).bind(items[position] as BasicSeriesInfo)
        }
    }

    override fun getItemViewType(position: Int) = items[position].getItemViewType()

    override fun getItemCount() = items.size

    inner class BannerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView by bindView(R.id.bannerItemImage)
        val title: TextView by bindView(R.id.bannerItemTitle)

        init {
            itemView.setOnClickListener {
                fragmentWeakRef.get()?.onRVItemClicked(items[adapterPosition], image)
            }
        }

        fun bind(basicSeriesInfo: BasicSeriesInfo) {
            title.text = basicSeriesInfo.name
            Glide.with(fragmentWeakRef.get())
                    .load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
                    .into(image)
        }
    }
}