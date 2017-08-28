package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.fragmentTransaction
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.Fragments.BrowseMoviesFragment
import com.bonnetrouge.toonup.Fragments.BrowseSeriesFragment
import com.bonnetrouge.toonup.Fragments.BrowseTvFragment
import com.bonnetrouge.toonup.Fragments.CategoryChooserFragment
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : BaseActivity() {

	@Inject lateinit var categoryChooserFragment: CategoryChooserFragment
	@Inject lateinit var browseTvFragment: BrowseTvFragment

	@Inject lateinit var browseViewModelFactory: BrowseViewModelFactory
	lateinit var browseViewModel: BrowseViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_browse)
		app.component.plus(BrowseActivityModule()).inject(this)
		browseViewModel = ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
		fragmentTransaction { replace(browseFragmentContainer.id, categoryChooserFragment) }
	}

	fun navigateTvShows() {
		fragmentTransaction { replace(browseFragmentContainer.id, browseTvFragment) }
	}

	fun navigateMovies() {
		dog("MOVIES")
	}
}
