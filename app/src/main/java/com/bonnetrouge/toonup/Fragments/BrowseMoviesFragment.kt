package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Activities.DetailActivity
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.VideoGenres
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.BannerListAdapter
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_browse_movies.*
import javax.inject.Inject

class BrowseMoviesFragment @Inject constructor(): BaseFragment() {

	val browseViewModel by lazy { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }
	val bannerListAdapter by lazy { BannerListAdapter(this) }

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
		browseViewModel.ensureGenresNotNull( { onGenresSuccess(it) }, { onGenresFailure() } )
	}

	fun onGenresSuccess(videoGenres: VideoGenres) {
		browseViewModel.getAllMovies()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retry(3)
				.doFinally { hideLoading() }
				.flatMap {
					val moviesByGenre = mutableListOf<BannerModel>()
					for (videoGenre in videoGenres.genres) {
						val moviesList = it.filter({ it.genres.contains(videoGenre) }).toMutableList()
						moviesList.sortByDescending { it.rating }
						if (moviesList.size > 0) moviesByGenre.add(BannerModel(videoGenre, moviesList))
					}
					Observable.fromArray(moviesByGenre)
				}
				.subscribe({
					hideErrorMsg()
					for (bannerModel in it) {
						with (bannerListAdapter.banners) {
							add(bannerModel)
							bannerListAdapter.notifyItemInserted(size - 1)
						}
					}
				}, {
					showErroMsg()
				})
	}

	fun onGenresFailure() {
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

	override fun onRecyclerViewItemClicked(item: RVItem) {
		(activity as BrowseActivity).navigateDetail(item as BasicSeriesInfo)
	}
}