package com.bonnetrouge.toonup.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Commons.Ext.isInViewBounds
import com.bonnetrouge.toonup.R
import kotlinx.android.synthetic.main.fragment_category_chooser.*
import javax.inject.Inject

class CategoryChooserFragment @Inject constructor() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_category_chooser, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachClickAnimation(tvShowsChooserContainer) { (activity as BrowseActivity).navigateTvShows() }
        attachClickAnimation(moviesChooserContainer) { (activity as BrowseActivity).navigateMovies() }
        attachClickAnimation(animeChooserContainer) { (activity as BrowseActivity).navigateAnime() }
        (activity as BrowseActivity).hideBackButton()
    }

    fun attachClickAnimation(view: View?, onUp: () -> Unit) {
        view?.setOnTouchListener { v, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    v?.scaleX = 0.98f
                    v?.scaleY = 0.98f
                }
                MotionEvent.ACTION_MOVE ->
                    if (!isInViewBounds(v, motionEvent.rawX.toInt(), motionEvent.rawY.toInt())) {
                        v?.scaleX = 1f
                        v?.scaleY = 1f
                    } else {
                        v?.scaleX = 0.98f
                        v?.scaleY = 0.98f
                    }
                MotionEvent.ACTION_UP ->
                    if (isInViewBounds(v, motionEvent.rawX.toInt(), motionEvent.rawY.toInt())) {
                        v?.scaleX = 1f
                        v?.scaleY = 1f
                        onUp()
                    }
            }
            true
        }
    }
}