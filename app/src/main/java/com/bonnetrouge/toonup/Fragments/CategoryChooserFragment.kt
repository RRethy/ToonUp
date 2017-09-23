package com.bonnetrouge.toonup.Fragments
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.R
import kotlinx.android.synthetic.main.fragment_category_chooser.*
import javax.inject.Inject

class CategoryChooserFragment @Inject constructor(): Fragment() {

	val backgroundAnimation by lazy { rootChooserLayout.background as AnimationDrawable }

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_category_chooser, container, false)

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		with (backgroundAnimation) {
            setEnterFadeDuration(5000)
			setExitFadeDuration(5000)
		}
		tvShowsChooserContainer.setOnClickListener { (activity as BrowseActivity).navigateTvShows() }
		moviesChooserContainer.setOnClickListener { (activity as BrowseActivity).navigateMovies() }
	}

    override fun onResume() {
        super.onResume()
        backgroundAnimation.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundAnimation.stop()
    }
}