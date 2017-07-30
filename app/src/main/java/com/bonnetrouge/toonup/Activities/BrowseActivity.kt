package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.fragmentTransaction
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.Fragments.BrowseMoviesFragment
import com.bonnetrouge.toonup.Fragments.BrowseRecentsFragment
import com.bonnetrouge.toonup.Fragments.BrowseSeriesFragment
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : BaseActivity() {

	@Inject
	lateinit var browseSeriesFragment: BrowseSeriesFragment
	@Inject
	lateinit var browseMoviesFragment: BrowseMoviesFragment
	@Inject
	lateinit var browseRecentsFragment: BrowseRecentsFragment
	@Inject
	lateinit var browseViewModelFactory: BrowseViewModelFactory
	lateinit var browseViewModel: BrowseViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_browse)
		app.component.plus(BrowseActivityModule()).inject(this)
		browseViewModel = ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
		setSupportActionBar(toolbar)
		fragmentTransaction {
			replace(fragmentContainer.id, browseSeriesFragment)
		}
		bottomNavView.setOnNavigationItemSelectedListener {
			when (it.itemId) {
				R.id.action_browse_tv_shows -> {
					fragmentTransaction {
						setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
						replace(fragmentContainer.id, browseSeriesFragment)
					}
					true
				}
				R.id.action_browse_movies -> {
					fragmentTransaction {
						setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
						replace(fragmentContainer.id, browseMoviesFragment)
					}
					true
				}
				R.id.action_browse_recents -> {
					fragmentTransaction {
						setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_shrink_fade_out_from_bottom)
						replace(fragmentContainer.id, browseRecentsFragment)
					}
					true
				}
				else -> false
			}
		}
	}
}
