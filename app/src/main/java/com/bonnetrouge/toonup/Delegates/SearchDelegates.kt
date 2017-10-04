package com.bonnetrouge.toonup.Delegates

import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel

interface SearchDelegate {
    fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>) -> Unit)
}

class CartoonSearchDelegate : SearchDelegate {
    override fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>) -> Unit) {
        onResult(viewModel.getCartoonSearchResults(s))
    }
}

class MoviesSearchDelegate : SearchDelegate {
    override fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>) -> Unit) {
        onResult(viewModel.getMoviesSearchResults(s))
    }
}

class AnimeSearchDelegate : SearchDelegate {
    override fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>) -> Unit) {
        onResult(viewModel.getAnimeSearchResult(s))
    }
}
