package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.fragmentTransaction
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.Fragments.BrowseTvFragment
import com.bonnetrouge.toonup.Fragments.CategoryChooserFragment
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject
import com.bonnetrouge.toonup.R.id.toolbar
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import com.bonnetrouge.toonup.Fragments.BrowseMoviesFragment


class BrowseActivity : BaseActivity() {

	@Inject lateinit var categoryChooserFragment: CategoryChooserFragment
	@Inject lateinit var browseTvFragment: BrowseTvFragment
	@Inject lateinit var browseMoviesFragment: BrowseMoviesFragment

	@Inject lateinit var browseViewModelFactory: BrowseViewModelFactory
	lateinit var browseViewModel: BrowseViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_browse)
		app.component.plus(BrowseActivityModule()).inject(this)
		browseViewModel = ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
		browseViewModel.prefetchGenres()
		setSupportActionBar(toolbar)
		fragmentTransaction(false) { replace(browseFragmentContainer.id, categoryChooserFragment) }
	}

	override fun onBackPressed() {
		super.onBackPressed()
		supportActionBar?.setDisplayHomeAsUpEnabled(false)
	}

	fun navigateTvShows() {
		fragmentTransaction { replace(browseFragmentContainer.id, browseTvFragment) }
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	fun navigateMovies() {
		fragmentTransaction { replace(browseFragmentContainer.id, browseMoviesFragment) }
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	fun navigateDetail(basicSeriesInfo: BasicSeriesInfo) {

	}
}
