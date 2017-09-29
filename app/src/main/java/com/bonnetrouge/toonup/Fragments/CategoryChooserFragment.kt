package com.bonnetrouge.toonup.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.R
import kotlinx.android.synthetic.main.fragment_category_chooser.*
import javax.inject.Inject

class CategoryChooserFragment @Inject constructor(): Fragment() {

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
			inflater?.inflate(R.layout.fragment_category_chooser, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
		tvShowsChooserContainer.setOnClickListener {
			tvShowsChooserContainer.animate().scaleX(0.9f).scaleY(0.9f).setDuration(250).start()
			(activity as BrowseActivity).navigateTvShows()
		}
		moviesChooserContainer.setOnClickListener {
			moviesChooserContainer.animate().scaleX(0.9f).scaleY(0.9f).setDuration(250).start()
			(activity as BrowseActivity).navigateMovies()
		}
		animeChooserContainer.setOnClickListener {
			animeChooserContainer.animate().scaleX(0.9f).scaleY(0.9f).setDuration(250).start()
			(activity as BrowseActivity).navigateAnime()
		}
	}
}