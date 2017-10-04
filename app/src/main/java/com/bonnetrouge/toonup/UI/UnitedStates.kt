package com.bonnetrouge.toonup.UI

import com.bonnetrouge.toonup.Delegates.AnimeSearchDelegate
import com.bonnetrouge.toonup.Delegates.CartoonSearchDelegate
import com.bonnetrouge.toonup.Delegates.MoviesSearchDelegate
import javax.inject.Inject

class UnitedStates @Inject constructor() {

    var state: Int = NO_STATE

    companion object {
        val CURRENT_BROWSE_STATE = "CURRENT BROWSING STATE"
        val IS_SEARCH_ICON_SHOWING = "IS SEARCH ICON SHOWING"
        val NO_STATE = -1
        val CATEGORY_CHOOSER_STATE = 0
        val BROWSE_TV_STATE = 1
        val BROWSE_MOVIES_STATE = 2
        val BROWSE_ANIME_STATE = 3
        val SEARCH_TV_STATE = 4
        val SEARCH_MOVIES_STATE = 5
        val SEARCH_ANIME_STATES = 6
    }

    fun isSearching() = state == SEARCH_TV_STATE || state == SEARCH_MOVIES_STATE || state == SEARCH_ANIME_STATES

    fun isBrowsing() = state == BROWSE_TV_STATE || state == BROWSE_MOVIES_STATE || state == BROWSE_ANIME_STATE

    fun returnFromSearch() {
        when (state) {
            SEARCH_TV_STATE -> state = BROWSE_TV_STATE
            SEARCH_MOVIES_STATE -> state = BROWSE_MOVIES_STATE
            SEARCH_ANIME_STATES -> state = BROWSE_ANIME_STATE
        }
    }

    fun goToSearch() {
        when (state) {
            BROWSE_TV_STATE -> state = SEARCH_TV_STATE
            BROWSE_MOVIES_STATE -> state = SEARCH_MOVIES_STATE
            BROWSE_ANIME_STATE -> state = SEARCH_ANIME_STATES
        }
    }

    fun returnToChooser() {
        state = CATEGORY_CHOOSER_STATE
    }

    fun intializeBaseState() {
        state = CATEGORY_CHOOSER_STATE
    }

    fun updateState(currentState: Int) {
        state = currentState
    }

    fun getStateSafeSearchDelegate() = when (state) {
        BROWSE_TV_STATE -> CartoonSearchDelegate()
        BROWSE_MOVIES_STATE -> MoviesSearchDelegate()
        else -> AnimeSearchDelegate()
    }
}
