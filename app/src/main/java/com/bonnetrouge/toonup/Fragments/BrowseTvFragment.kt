package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Commons.Ext.DTAG
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.shuffle
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.VideoGenres
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.BannerListAdapter
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import io.reactivex.Observable
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
		tvRecyclerView.adapter = bannerListAdapter
		tvRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		swipeRefreshLayout.setOnRefreshListener { refreshBanners() }
		refreshBanners()
	}

	fun refreshBanners() {
		showLoading()
		browseViewModel.ensureGenresNotNull( { onGenresSuccess(it) }, { onGenresFailure() } )
	}

	fun onGenresSuccess(videoGenres: VideoGenres) {
		getAllSeries(videoGenres)
	}

	fun onGenresFailure() {
		hideLoading()
		showErroMsg()
	}

	fun getAllSeries(videoGenres: VideoGenres) {
		browseViewModel.getAllCartoonsObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.retry(3)
				.doFinally {
					hideLoading()
				}
				.flatMap {
					val seriesByGenre = HashMap<String, MutableList<BasicSeriesInfo>>()
					for (videoGenre in videoGenres.genres) {
						val seriesList = it.filter({ it.genres.contains(videoGenre) }).toMutableList()
						if (seriesList.size > 0) seriesByGenre.put(videoGenre, seriesList)
						seriesByGenre[videoGenre]?.shuffle() //TODO: This crashes with IndexOutOfBounds
					}
					Observable.fromArray(seriesByGenre)
				}
				.subscribe({
					hideErrorMsg()
					for ((genre, listOfSeries) in it) {
						with (bannerListAdapter.banners) {
							add(BannerModel(listOfSeries, genre))
							bannerListAdapter.notifyItemInserted(size - 1)
						}
					}
				}, {
					showErroMsg()
				})
	}

	fun showErroMsg() {
		errorMessage?.visibility = View.VISIBLE
	}

	fun hideErrorMsg() {
		errorMessage?.visibility = View.INVISIBLE
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
