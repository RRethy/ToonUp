package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth
import com.bonnetrouge.toonup.Model.ListItemTypes
import com.bonnetrouge.toonup.Model.LoadingItem
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.VeryBasicAdapter
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_browse_series.*
import javax.inject.Inject
import com.bonnetrouge.toonup.UI.SeriesAdapter


class BrowseSeriesFragment @Inject constructor(): Fragment() {

	val seriesAdapter by lazy {
		SeriesAdapter(context)
	}
	val browseViewModel by lazy {
		ViewModelProviders.of(activity).get(BrowseViewModel::class.java)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater?.inflate(R.layout.fragment_browse_series, container, false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		browseSeriesRecyclerView.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
		(browseSeriesRecyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
			override fun getSpanSize(position: Int)
					= if (seriesAdapter.itemList[position].getDataType() == ListItemTypes.LOADING) 3 else 1
		}
		browseSeriesRecyclerView.adapter = seriesAdapter
		populateRecyclerView()
	}

	fun populateRecyclerView() {
		if (browseViewModel.popularCartoons != null) {
			seriesAdapter.itemList.addAll(browseViewModel.popularCartoons!!)
			seriesAdapter.notifyDataSetChanged()
		} else {
			browseViewModel.getPopularCartoonObservable()
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.doOnSubscribe {
						seriesAdapter.itemList.add(LoadingItem())
						seriesAdapter.notifyItemInserted(0)
					}
					.subscribe({
						seriesAdapter.itemList.removeAt(0)
						seriesAdapter.notifyItemRemoved(0)
						seriesAdapter.itemList.addAll(it)
						seriesAdapter.notifyItemRangeInserted(0, it.size)
					},{
						//TODO: Add error handling
						Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
					})
		}
	}
}