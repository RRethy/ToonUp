package com.bonnetrouge.toonup.Adapters

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.bindView
import com.bonnetrouge.toonup.Fragments.BrowsingFragment
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItemViewTypes
import java.lang.ref.WeakReference

class BannerListAdapter(fragment: BrowsingFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val fragmentWeakRef = WeakReference<BrowsingFragment>(fragment)
    val banners = mutableListOf<BannerModel>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            RVItemViewTypes.BANNER -> {
                val holder = BannerViewHolder(LayoutInflater.from(parent?.context)
                        .inflate(R.layout.view_holder_banner, parent, false))
                return holder
            }
        }
        return null
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            RVItemViewTypes.BANNER -> (holder as BannerViewHolder).bind(banners[position])
        }
    }

    override fun getItemViewType(position: Int) = banners[position].getItemViewType()

    override fun getItemCount() = banners.size

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val layoutManager = LinearLayoutManager(app.applicationContext,
                LinearLayoutManager.HORIZONTAL,
                false)
        val bannerItemsAdapter = BannerItemsAdapter(fragmentWeakRef.get())
        //val snapHelper = GravitySnapHelper(Gravity.START)

        val bannerRecyclerView: RecyclerView by bindView(R.id.bannerRecyclerView)
        val bannerTitle: TextView by bindView(R.id.bannerTitle)

        init {
            bannerRecyclerView.layoutManager = layoutManager
            bannerRecyclerView.adapter = bannerItemsAdapter
            //snapHelper.attachToRecyclerView(bannerRecyclerView)
        }

        fun bind(bannerModel: BannerModel) {
            bannerTitle.text = bannerModel.title
            bannerItemsAdapter.items.clear()
            bannerItemsAdapter.items.addAll(bannerModel.dataList)
            bannerItemsAdapter.notifyDataSetChanged()
        }
    }
}
