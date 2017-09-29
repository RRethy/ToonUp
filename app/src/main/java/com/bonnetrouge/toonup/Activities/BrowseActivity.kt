package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import com.bonnetrouge.toonup.Commons.Ext.*
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.Fragments.BrowseAnimeFragment
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
    @Inject lateinit var browseAnimeFragment: BrowseAnimeFragment

    val backgroundAnimation by lazy { rootBackground.background as AnimationDrawable }

	@Inject lateinit var browseViewModelFactory: BrowseViewModelFactory
	lateinit var browseViewModel: BrowseViewModel

	companion object {
        fun navigate(context: Context) {
			val intent = Intent(context, BrowseActivity::class.java)
			context.startActivity(intent)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        app.component.plus(BrowseActivityModule()).inject(this)
        browseViewModel = ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
        browseViewModel.prefetchGenres()
        setSupportActionBar(toolbar)
        savedInstanceState.ifNull { fragmentTransaction(false) { replace(browseFragmentContainer.id, categoryChooserFragment) } }
        with(backgroundAnimation) {
            setEnterFadeDuration(1000)
            setExitFadeDuration(4000)
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

	fun navigateAnime() {
		fragmentTransaction { replace(browseFragmentContainer.id, browseAnimeFragment) }
	}

	fun navigateDetail(basicSeriesInfo: BasicSeriesInfo, imageView: ImageView) {
		DetailActivity.navigate(this, basicSeriesInfo, imageView)
	}
}
