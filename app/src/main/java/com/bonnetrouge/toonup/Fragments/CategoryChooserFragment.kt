package com.bonnetrouge.toonup.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Commons.Ext.isInViewBounds
import com.bonnetrouge.toonup.R
import kotlinx.android.synthetic.main.fragment_category_chooser.*
import javax.inject.Inject

class CategoryChooserFragment @Inject constructor(): Fragment() {

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
			inflater?.inflate(R.layout.fragment_category_chooser, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
		attachClickAnimation(tvShowsChooserContainer) { (activity as BrowseActivity).navigateTvShows() }
		attachClickAnimation(moviesChooserContainer) { (activity as BrowseActivity).navigateMovies() }
		attachClickAnimation(animeChooserContainer) { (activity as BrowseActivity).navigateAnime() }
	}

	fun attachClickAnimation(view: View?, onUp: () -> Unit) {
		view?.setOnTouchListener { view, motionEvent ->
			when (motionEvent.action) {
				MotionEvent.ACTION_DOWN -> {
					view?.scaleX = 0.98f
					view?.scaleY = 0.98f
				}
				MotionEvent.ACTION_MOVE ->
					if (!isInViewBounds(view, motionEvent.rawX.toInt(), motionEvent.rawY.toInt())) {
						view?.scaleX = 1f
						view?.scaleY = 1f
					} else {
						view?.scaleX = 0.98f
						view?.scaleY = 0.98f
					}
				MotionEvent.ACTION_UP ->
					if (isInViewBounds(view, motionEvent.rawX.toInt(), motionEvent.rawY.toInt())) {
						view?.scaleX = 1f
						view?.scaleY = 1f
						onUp()
					}
			}
			true
		}
	}
}