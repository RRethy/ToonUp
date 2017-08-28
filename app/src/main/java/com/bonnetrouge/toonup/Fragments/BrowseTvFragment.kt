package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import javax.inject.Inject

class BrowseTvFragment @Inject constructor(): Fragment(), OnRecyclerViewItemClicked {

	val browseViewModel by lazy { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
			= inflater?.inflate(R.layout.fragment_browse_tv, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
	}

	override fun onRecyclerViewItemClicked(item: Any) {
		dog(item.toString())
	}
}
