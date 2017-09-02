package com.bonnetrouge.toonup.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem

class DetailSeriesFragment : BaseFragment() {

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
			= inflater?.inflate(R.layout.fragment_detail_series, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun onRecyclerViewItemClicked(item: RVItem) {

	}
}