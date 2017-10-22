package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.bonnetrouge.toonup.Commons.Ext.*
import com.bonnetrouge.toonup.DI.Modules.BrowseActivityModule
import com.bonnetrouge.toonup.Fragments.*
import com.bonnetrouge.toonup.Listeners.DebounceTextWatcher
import com.bonnetrouge.toonup.Listeners.OnSearchDebounceListener
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.UnitedStates
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : BaseActivity(), DebounceTextWatcher.OnDebouncedListener {

    @Inject lateinit var categoryChooserFragment: CategoryChooserFragment
    @Inject lateinit var browsingFragment: BrowsingFragment
    @Inject lateinit var searchFragment: SearchFragment

    @Inject lateinit var stateMachine: UnitedStates

    @Inject lateinit var browseViewModelFactory: BrowseViewModelFactory
    lateinit var browseViewModel: BrowseViewModel

    val backgroundAnimation by lazyAndroid { rootBackground.background as AnimationDrawable }
    val debounceTextWatcher by lazyAndroid { DebounceTextWatcher(this) }

    var searchItem: MenuItem? = null

    var searchListener: OnSearchDebounceListener? = null

    companion object {
        fun navigate(context: Context) {
            val intent = Intent(context, BrowseActivity::class.java)
            context.startActivity(intent)
        }
    }

    //region Activity methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        app.component.plus(BrowseActivityModule()).inject(this)
        browseViewModel = ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
        browseViewModel.prefetchGenres()
        setSupportActionBar(toolbar)
        savedInstanceState.ifNullElse({
            initBaseUI()
        }, {
            onPostRotation(savedInstanceState!!)
        })
        setupSickBackgroundGradients()
        setupSearchTextWatcher()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(UnitedStates.CURRENT_BROWSE_STATE, stateMachine.state)
        outState?.putBoolean(UnitedStates.IS_SEARCH_ICON_SHOWING, searchItem.safeBool { isVisible })
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.browse_menu_options, menu)
        searchItem = menu.findItem(R.id.search_item)
        searchItem?.setOnMenuItemClickListener {
            showSearchToolbar()
            navigateSearch()
            true
        }
        updateSearchIconVisibility()
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
    //endregion

    //region Debounce callbacks
    override fun onDebounced(s: CharSequence) {
        runOnUiThread {
            searchListener?.onSearchDebounce(s)
        }
    }

    override fun onPreDebounce(s: CharSequence) {
        if (s.isEmpty()) {
            clearIcon.invisible()
        } else {
            clearIcon.visible()
        }
    }
    //endregion

    //region UI Initialization
    fun initBaseUI() {
        hideBackButton()
        navigateCategoryChooser()
    }

    fun onPostRotation(savedInstanceState: Bundle) {
        stateMachine.updateState(savedInstanceState.getInt(UnitedStates.CURRENT_BROWSE_STATE))
    }

    fun setupSickBackgroundGradients() {
        backgroundAnimation.with {
            setEnterFadeDuration(5000)
            setExitFadeDuration(5000)
        }
    }

    fun setupSearchTextWatcher() {
        searchEditText.addTextChangedListener(debounceTextWatcher)
        clearIcon.setOnClickListener { searchEditText.setText("") }
    }

    fun updateSearchIconVisibility() {
        if (stateMachine.isBrowsing()) {
            showSearchIcon()
        } else {
            hideSearchIcon()
        }
    }
    //endregion

    //region UI Convenience Methods
    fun showSearchToolbar() {
        searchItem?.isVisible = false
        searchTextContainer.with {
            visible()
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
                gone()
                showSearchIcon()
            }.start()
        }
    }

    fun showSearchIcon() {
        searchItem?.isVisible = true
    }

    fun hideSearchIcon() {
        searchItem?.isVisible = false
    }
    //endregion

    //region Fragment/Activity Navigation
    fun navigateCategoryChooser() {
        stateMachine.intializeBaseState()
        fragmentTransaction(false) { replace(browseFragmentContainer.id, categoryChooserFragment) }
    }

    fun navigateTvShows() {
        stateMachine.updateState(UnitedStates.BROWSE_TV_STATE)
        browsingFragment.bannerListAdapter.banners.clear()
        fragmentTransaction {
            setCustomAnimations(R.anim.fade_slide_in_bottom, R.anim.my_fade_out, R.anim.my_400_fade_in, R.anim.fade_slide_out_bottom)
            replace(browseFragmentContainer.id, browsingFragment)
        }
    }

    fun navigateMovies() {
        stateMachine.updateState(UnitedStates.BROWSE_MOVIES_STATE)
        browsingFragment.bannerListAdapter.banners.clear()
        fragmentTransaction {
            setCustomAnimations(R.anim.fade_slide_in_bottom, R.anim.my_fade_out, R.anim.my_400_fade_in, R.anim.fade_slide_out_bottom)
            replace(browseFragmentContainer.id, browsingFragment)
        }
    }

    fun navigateAnime() {
        stateMachine.updateState(UnitedStates.BROWSE_ANIME_STATE)
        browsingFragment.bannerListAdapter.banners.clear()
        fragmentTransaction {
            setCustomAnimations(R.anim.fade_slide_in_bottom, R.anim.my_fade_out, R.anim.my_400_fade_in, R.anim.fade_slide_out_bottom)
            replace(browseFragmentContainer.id, browsingFragment)
        }
    }

    fun navigateSearch() {
        stateMachine.goToSearch()
        fragmentTransaction {
            setCustomAnimations(R.anim.my_300_fade_in, R.anim.my_fade_out, R.anim.my_300_fade_in, R.anim.my_fade_out)
            replace(browseFragmentContainer.id, searchFragment)
        }
    }

    fun navigateDetail(basicSeriesInfo: BasicSeriesInfo, imageView: ImageView) {
        if (isConnected()) {
            DetailActivity.navigate(this, basicSeriesInfo, imageView)
        } else {
            longToast(R.string.connectivity_toast_msg)
        }
    }
    //endregion
}
