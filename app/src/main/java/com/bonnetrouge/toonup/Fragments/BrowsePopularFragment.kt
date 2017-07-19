package com.bonnetrouge.toonup.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.R

class BrowsePopularFragment: Fragment() {

	override fun onAttach(context: Context?) {
		super.onAttach(context)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		var view = inflater?.inflate(R.layout.fragment_browse_popular, container, false)
		return view
	}
}