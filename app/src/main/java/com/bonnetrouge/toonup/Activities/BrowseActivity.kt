package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import com.bonnetrouge.toonup.Commons.Ext.*
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject
import android.view.MenuItem
import android.view.View
import com.bonnetrouge.toonup.Fragments.*
import com.bonnetrouge.toonup.Listeners.DebounceTextWatcher
import com.bonnetrouge.toonup.UI.UnitedStates
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory

class BrowseActivity : BaseActivity(), DebounceTextWatcher.OnDebouncedListener {

	@Inject lateinit var categoryChooserFragment: CategoryChooserFragment
	@Inject lateinit var browseTvFragment: BrowseTvFragment
	@Inject lateinit var browseMoviesFragment: BrowseMoviesFragment
	@Inject lateinit var browseAnimeFragment: BrowseAnimeFragment
	@Inject lateinit var searchFragment: SearchFragment

    @Inject lateinit var stateMachine: UnitedStates

	@Inject lateinit var browseViewModelFactory: BrowseViewModelFactory
	lateinit var browseViewModel: BrowseViewModel

	val backgroundAnimation by lazyAndroid { rootBackground.background as AnimationDrawable }
	val debounceTextWatcher by lazyAndroid { DebounceTextWatcher(this) }

	lateinit var searchItem: MenuItem

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
		savedInstanceState.ifNullElse({
			supportActionBar?.setDisplayHomeAsUpEnabled(false)
			fragmentTransaction(false) { replace(browseFragmentContainer.id, categoryChooserFragment) }
            stateMachine.intializeBaseState()
		}, {
			savedInstanceState?.let {
				stateMachine.updateState(savedInstanceState.getInt(UnitedStates.CURRENT_BROWSE_STATE))
			}
		})
		with(backgroundAnimation) {
			setEnterFadeDuration(1000)
			setExitFadeDuration(4000)
		}
		setupSearchTextWatcher()
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		outState?.putInt(UnitedStates.CURRENT_BROWSE_STATE, stateMachine.state)
		super.onSaveInstanceState(outState)
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.browse_menu_options, menu)
		searchItem = menu.findItem(R.id.search_item)
		searchItem.setOnMenuItemClickListener {
			showSearchToolbar(searchItem)
			navigateSearch()
			true
		}
		hideSearchIcon()

		return true
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
		if (stateMachine.isSearching()) {
			hideSearchToolbar()
            stateMachine.returnFromSearch()
		} else {
			hideSearchIcon()
			supportActionBar?.setDisplayHomeAsUpEnabled(false)
            stateMachine.returnToChooser()
		}
		super.onBackPressed()
	}

	override fun onDebounced(s: CharSequence) {
		runOnUiThread({
            if (stateMachine.isSearching()) searchFragment.dispatchSearch(s)
		})
	}

	override fun onPreDebounce(s: CharSequence) {
		if (s.isEmpty()) {
			clearIcon.visibility = View.INVISIBLE
		} else {
			clearIcon.visibility = View.VISIBLE
		}
	}

	fun setupSearchTextWatcher() {
		searchEditText.addTextChangedListener(debounceTextWatcher)
		clearIcon.setOnClickListener { searchEditText.setText("") }
	}

	fun showSearchToolbar(searchItem: MenuItem) {
		searchItem.isVisible = false
		searchTextContainer.with {
			visibility = View.VISIBLE
			pivotX = getDisplayWidth().toFloat() * 0.75f
			animate().scaleX(1f).setDuration(300).withEndAction {
				searchEditText.showKeyboard()
				searchEditText.setHint(R.string.search_hint)
			}.start()
		}
	}

	fun hideSearchToolbar() {
		searchEditText.with {
            hideKeyboard()
			setText("")
			hint = ""
			isFocusableInTouchMode = false
			isFocusable = false
			isFocusableInTouchMode = true
			isFocusable = true
		}
		searchTextContainer.with {
			animate().scaleX(0f).setDuration(300).withEndAction {
				visibility = View.GONE
				searchItem.isVisible = true
			}.start()
		}
	}

	fun showSearchIcon() {
		searchItem.isVisible = true
	}

	fun hideSearchIcon() {
		searchItem.isVisible = false
	}

	fun navigateTvShows() {
		fragmentTransaction {
			setCustomAnimations(R.anim.fade_slide_in_bottom, R.anim.fade_slide_out_bottom, R.anim.fade_slide_in_bottom, R.anim.fade_slide_out_bottom)
			replace(browseFragmentContainer.id, browseTvFragment)
		}
		stateMachine.updateState(UnitedStates.BROWSE_TV_STATE)
		showBackButton()
		showSearchIcon()
	}

	fun navigateMovies() {
		fragmentTransaction {
			setCustomAnimations(R.anim.fade_slide_in_bottom, R.anim.fade_slide_out_bottom, R.anim.fade_slide_in_bottom, R.anim.fade_slide_out_bottom)
			replace(browseFragmentContainer.id, browseMoviesFragment)
		}
		stateMachine.updateState(UnitedStates.BROWSE_MOVIES_STATE)
		showBackButton()
		showSearchIcon()
	}

	fun navigateAnime() {
		fragmentTransaction {
			setCustomAnimations(R.anim.fade_slide_in_bottom, R.anim.fade_slide_out_bottom, R.anim.fade_slide_in_bottom, R.anim.fade_slide_out_bottom)
			replace(browseFragmentContainer.id, browseAnimeFragment)
		}
		stateMachine.updateState(UnitedStates.BROWSE_ANIME_STATE)
		showBackButton()
		showSearchIcon()
	}

	fun navigateSearch() {
		fragmentTransaction {
            setCustomAnimations(R.anim.my_fade_in, R.anim.my_fade_out, R.anim.my_fade_in, R.anim.my_fade_out)
			replace(browseFragmentContainer.id, searchFragment)
		}
		stateMachine.state = UnitedStates.SEARCH_TV_STATE
	}

	fun navigateDetail(basicSeriesInfo: BasicSeriesInfo, imageView: ImageView) {
		DetailActivity.navigate(this, basicSeriesInfo, imageView)
	}
}
