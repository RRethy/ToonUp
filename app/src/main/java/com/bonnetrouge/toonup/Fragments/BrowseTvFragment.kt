package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.BannerListAdapter
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_browse_tv.*
import kotlinx.android.synthetic.main.view_holder_banner.*
import javax.inject.Inject

class BrowseTvFragment @Inject constructor(): BaseFragment(), OnRecyclerViewItemClicked {

	val browseViewModel by lazy { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }
	val bannerListAdapter by lazy { BannerListAdapter(this) }

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
			= inflater?.inflate(R.layout.fragment_browse_tv, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bannerRecyclerView.adapter = bannerListAdapter
		bannerRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		swipeRefreshLayout.setOnRefreshListener { refreshBanners() }
		refreshBanners()
	}

	fun refreshBanners() {
		browseViewModel.getAllCartoonsObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnSubscribe { showLoading() }
				.doFinally { hideLoading() }
				.subscribe({

				}, {

				})
	}

	fun showLoading() {
		swipeRefreshLayout.isRefreshing = true
	}

	fun hideLoading() {
		swipeRefreshLayout.isRefreshing = false
	}

	override fun onRecyclerViewItemClicked(item: Any) {
		dog(item.toString())
	}
}
