package com.bonnetrouge.toonup.Delegates

import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel

sealed class SearchDelegate {
    abstract fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>?) -> Unit)
}

class CartoonSearchDelegate : SearchDelegate() {
    override fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>?) -> Unit) {
        onResult(viewModel.getSearchResults(CartoonsVMDelegate(), s))
    }
}

class MoviesSearchDelegate : SearchDelegate() {
    override fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>?) -> Unit) {
        onResult(viewModel.getSearchResults(MoviesVMDelegate(), s))
    }
}

class AnimeSearchDelegate : SearchDelegate() {
    override fun getFilteredSearchResults(s: CharSequence, viewModel: BrowseViewModel, onResult: (MutableList<BasicSeriesInfo>?) -> Unit) {
        onResult(viewModel.getSearchResults(AnimeVMDelegate(), s))
    }
}
