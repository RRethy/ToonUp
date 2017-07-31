package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Activities.DetailActivity
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_browse_series.*
import javax.inject.Inject
import com.bonnetrouge.toonup.UI.SeriesAdapter

class BrowseSeriesFragment @Inject constructor(): Fragment(), OnRecyclerViewItemClicked {

	val seriesAdapter by lazy { SeriesAdapter(this) }
	val browseViewModel by lazy { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater?.inflate(R.layout.fragment_browse_series, container, false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		browseSeriesRecyclerView.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
		browseSeriesRecyclerView.adapter = seriesAdapter
		swipeRefreshLayout.setOnRefreshListener { refreshRecyclerView() }
		refreshRecyclerView()
	}

	fun refreshRecyclerView() {
		if (seriesAdapter.itemList.isEmpty()) {
			browseViewModel.getPopularCartoonObservable()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.doOnSubscribe { showLoading() }
					.doFinally { hideLoading() }
					.subscribe({
						hideErrorMsg()
						seriesAdapter.itemList.addAll(it)
						seriesAdapter.notifyItemRangeInserted(0, it.size)
					}, {
						showErroMsg()
					})
		} else {
			hideLoading()
		}
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

	override fun onRecyclerViewItemClicked(item: Any) {
		with (item as BasicSeriesInfo) {
			DetailActivity.navigate(context, item)
		}
	}
}