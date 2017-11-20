package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Adapters.SearchAdapter
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth
import com.bonnetrouge.toonup.Commons.Ext.lazyAndroid
import com.bonnetrouge.toonup.Commons.Ext.with
import com.bonnetrouge.toonup.Delegates.SearchDelegate
import com.bonnetrouge.toonup.Listeners.OnRVTransitionItemClicked
import com.bonnetrouge.toonup.Listeners.OnSearchDebounceListener
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.GridAutofitLayoutManager
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class SearchFragment @Inject constructor() : Fragment(), OnRVTransitionItemClicked, OnSearchDebounceListener {

    val browseViewModel by lazyAndroid { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }

    val itemWidth = getDisplayWidth() / 3
    val searchAdapter by lazyAndroid { SearchAdapter(this, itemWidth) }

    lateinit var searchDelegate: SearchDelegate

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRecyclerView.layoutManager = GridAutofitLayoutManager(itemWidth.toDouble())
        searchRecyclerView.adapter = searchAdapter
        (activity as BrowseActivity).with {
            searchDelegate = stateMachine.getStateSafeSearchDelegate()
            showSearchToolbar()
            showBackButton()
        }
        popularRecyclerView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        (activity as BrowseActivity).searchListener = this
    }

    override fun onDetach() {
        super.onDetach()
        (activity as BrowseActivity).searchListener = null
    }

    private fun popularRecyclerView() {
        searchDelegate.getFilteredSearchResults("", browseViewModel) {
            searchAdapter.with {
                if (it != null && it.isNotEmpty()) {
                    hideNoSearchResultsMsg()
                    items.clear()
                    items.addAll(it)
                    notifyDataSetChanged()
                } else {
                    showNoSearchResultsMsg()
                }
            }
        }
    }

    private fun dispatchSearch(s: CharSequence) {
        searchDelegate.getFilteredSearchResults(s, browseViewModel) {
            if (it != null && it.isNotEmpty()) {
                hideNoSearchResultsMsg()
                val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                            it[newItemPosition] == searchAdapter.items[oldItemPosition]

                    override fun getOldListSize() = searchAdapter.items.size

                    override fun getNewListSize() = it.size

                    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = true
                })
                searchAdapter.items.clear()
                searchAdapter.items.addAll(it)
                diffResult.dispatchUpdatesTo(searchAdapter)
            } else {
                showNoSearchResultsMsg()
            }
        }
    }

    private fun showNoSearchResultsMsg() {
        searchAdapter.items.clear()
        searchAdapter.notifyDataSetChanged()
        no_search_found_msg_container?.visibility = View.VISIBLE
    }

    private fun hideNoSearchResultsMsg() {
        no_search_found_msg_container?.visibility = View.INVISIBLE
    }

    override fun onSearchDebounce(s: CharSequence) {
        dispatchSearch(s)
    }

    override fun onRVItemClicked(item: RVItem, imageView: ImageView) {
        (activity as BrowseActivity).navigateDetail(item as BasicSeriesInfo, imageView)
    }
}
