package com.bonnetrouge.toonup.UI

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.R
import java.lang.ref.WeakReference

class BannerListAdapter(fragment: BaseFragment) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private val fragmentWeakRef = WeakReference<BaseFragment>(fragment)
	val banners = mutableListOf<RVItem>()

	override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
		when (viewType) {
			RVItemViewTypes.BANNER ->
				return BannerViewHolder(LayoutInflater.from(parent?.context)
						.inflate(R.layout.view_holder_banner, parent, false))
		}
		return null
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (getItemViewType(position)) {
			RVItemViewTypes.BANNER -> (holder as BannerViewHolder).bind(banners[position] as BannerModel)
		}
	}

	override fun getItemViewType(position: Int) = banners[position].getItemViewType()

	override fun getItemCount() = banners.size

	inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val layoutManager = LinearLayoutManager(fragmentWeakRef.get()?.context,
				LinearLayoutManager.VERTICAL,
				false)
		val bannerItemsAdapter = BannerItemsAdapter(fragmentWeakRef.get())
		val bannerRecyclerView = itemView.findViewById(R.id.bannerRecyclerView) as RecyclerView

		init {
			bannerRecyclerView.layoutManager = layoutManager
			bannerRecyclerView.adapter = bannerItemsAdapter
		}

		fun bind(bannerModel: BannerModel) {

		}
	}
}
