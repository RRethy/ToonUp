package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Commons.Ext.lazyAndroid
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.Adapters.BannerListAdapter
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import kotlinx.android.synthetic.main.fragment_browse_movies.*
import javax.inject.Inject

class BrowseMoviesFragment @Inject constructor(): BaseFragment() {

	val browseViewModel by lazyAndroid { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }
	val bannerListAdapter by lazyAndroid { BannerListAdapter(this) }

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
			= inflater?.inflate(R.layout.fragment_browse_movies, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		browseMoviesRecyclerView.adapter = bannerListAdapter
		browseMoviesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		swipeRefreshLayout.setOnRefreshListener { refreshBanners() }
		refreshBanners()
	}

	fun refreshBanners() {
		showLoading()
		browseViewModel.fetchMovies({
			this.subscribe({
				hideLoading()
				hideErrorMsg()
				swipeRefreshLayout.postDelayed({
					bannerListAdapter.banners.addAll(it)
					bannerListAdapter.notifyDataSetChanged()
				}, 150)
			}, {
				onNetworkError()
			})
		}, {
			onNetworkError()
		})
	}

	fun onNetworkError() {
		hideLoading()
		showErroMsg()
	}

	fun showErroMsg() {
		errorMessage?.visibility = View.VISIBLE
	}

	fun hideErrorMsg() {
		errorMessage?.visibility = View.INVISIBLE
	}

	fun showLoading() {
		swipeRefreshLayout?.isRefreshing = true
	}

	fun hideLoading() {
		swipeRefreshLayout?.isRefreshing = false
	}

	override fun onRVItemClicked(item: RVItem, imageView: ImageView) {
		(activity as BrowseActivity).navigateDetail(item as BasicSeriesInfo, imageView)
	}
}