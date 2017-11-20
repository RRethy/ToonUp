package com.bonnetrouge.toonup.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bonnetrouge.toonup.Activities.PlayerActivity
import com.bonnetrouge.toonup.Commons.bindView
import com.bonnetrouge.toonup.Model.LinkModelHolder
import com.bonnetrouge.toonup.Model.PartTitleModelHolder
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes
import java.lang.ref.WeakReference

class LinksAdapter(playerActivity: PlayerActivity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val playerActivityWeakRef = WeakReference<PlayerActivity>(playerActivity)
    val items = mutableListOf<RVItem>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            RVItemViewTypes.PART_TITLE -> return PartTitleViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.view_holder_part_title, parent, false))
            else -> return LinkViewHolder(LayoutInflater.from(parent?.context)
                    .inflate(R.layout.view_holder_link, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position].getItemViewType()) {
            RVItemViewTypes.PART_TITLE -> (holder as PartTitleViewHolder).bind(items[position] as PartTitleModelHolder)
            RVItemViewTypes.LINK -> (holder as LinkViewHolder).bind(items[position] as LinkModelHolder)
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getItemViewType()

    inner class LinkViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val linkTitle by bindView<TextView>(R.id.linkTitle)

        init {
            view.setOnClickListener {
                playerActivityWeakRef.get()?.onRecyclerViewItemClicked(items[adapterPosition])
            }
        }

        fun bind(linkModelHolder: LinkModelHolder) {
            linkTitle.text = linkModelHolder.linkTitle
        }
    }

    inner class PartTitleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val partTitle by bindView<TextView>(R.id.partTitle)

        fun bind(partTitleModelHolder: PartTitleModelHolder) {
            partTitle.text = partTitleModelHolder.partTitle
        }
    }
}
