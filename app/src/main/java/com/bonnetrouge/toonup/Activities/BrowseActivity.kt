package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import com.bonnetrouge.toonup.Commons.Ext.*
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.Fragments.BrowseTvFragment
import com.bonnetrouge.toonup.Fragments.CategoryChooserFragment
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject
import com.bonnetrouge.toonup.Fragments.BrowseMoviesFragment


class BrowseActivity : BaseActivity() {

	@Inject lateinit var categoryChooserFragment: CategoryChooserFragment
	@Inject lateinit var browseTvFragment: BrowseTvFragment
	@Inject lateinit var browseMoviesFragment: BrowseMoviesFragment

    val backgroundAnimation by lazy { rootBackground.background as AnimationDrawable }

	@Inject lateinit var browseViewModelFactory: BrowseViewModelFactory
	lateinit var browseViewModel: BrowseViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        app.component.plus(BrowseActivityModule()).inject(this)
        browseViewModel = ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
        browseViewModel.prefetchGenres()
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
			fragmentTransaction(false) { replace(browseFragmentContainer.id, categoryChooserFragment) }
        }
		with(backgroundAnimation) {
            setEnterFadeDuration(6500)
            setExitFadeDuration(6500)
			start()
        }
	}

    override fun onResume() {
        super.onResume()
		backgroundAnimation.start()
    }

    override fun onPause() {
        super.onPause()
		backgroundAnimation.stop()
    }

	override fun onBackPressed() {
		super.onBackPressed()
		supportActionBar?.setDisplayHomeAsUpEnabled(false)
	}

	fun navigateTvShows() {
		fragmentTransaction { replace(browseFragmentContainer.id, browseTvFragment) }
	}

	fun navigateMovies() {
		fragmentTransaction { replace(browseFragmentContainer.id, browseMoviesFragment) }
	}

	fun navigateDetail(basicSeriesInfo: BasicSeriesInfo) {
		DetailActivity.navigate(this, basicSeriesInfo)
	}
}
