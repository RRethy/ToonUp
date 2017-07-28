package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bonnetrouge.toonup.API.StreamingApiService
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.VeryBasicAdapter
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_browse_popular.*
import javax.inject.Inject

class BrowsePopularFragment @Inject constructor(): Fragment() {

	val veryBasicAdapter by lazy {
		VeryBasicAdapter(activity)
	}
	val browseViewModel by lazy {
		ViewModelProviders.of(activity).get(BrowseViewModel::class.java)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val itemView = inflater?.inflate(R.layout.fragment_browse_popular, container, false)

		return itemView
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		browsePopularRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
		browsePopularRecyclerView.adapter = veryBasicAdapter
		populateRecyclerView()
	}

	fun populateRecyclerView() {
		browseViewModel.getPopularCartoonObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					veryBasicAdapter.seriesItems.addAll(it)
					veryBasicAdapter.notifyDataSetChanged()
				},{
					Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
				})
	}
}