package com.bonnetrouge.toonup.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bonnetrouge.toonup.Commons.Ext.with
import com.bonnetrouge.toonup.Commons.bindView
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes
import com.bumptech.glide.Glide
import java.lang.ref.WeakReference

class SearchAdapter(baseFragment: BaseFragment, val itemWidth: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val fragmentWeakRef = WeakReference<BaseFragment>(baseFragment)
    val items = mutableListOf<RVItem>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            RVItemViewTypes.BASIC_SERIES -> return SearchItemViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.view_holder_search_item, parent, false))
        }
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)) {
            RVItemViewTypes.BASIC_SERIES -> (holder as SearchItemViewHolder).bind(items[position] as BasicSeriesInfo)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getItemViewType()

    inner class SearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView by bindView(R.id.searchImage)

        init {
            image.layoutParams.with {
                width = itemWidth
                height = Math.floor(itemWidth * 1.2).toInt()
            }
            image.setOnClickListener { fragmentWeakRef.get()?.onRVItemClicked(items[adapterPosition], image) }
        }

        fun bind(basicSeriesInfo: BasicSeriesInfo) {
            Glide.with(fragmentWeakRef.get())
                    .load("http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
                    .into(image)
        }
    }
}
