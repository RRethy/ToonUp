package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Adapters.BannerListAdapter
import com.bonnetrouge.toonup.Commons.Ext.ifAdded
import com.bonnetrouge.toonup.Commons.Ext.lazyAndroid
import com.bonnetrouge.toonup.Delegates.CartoonFetchingDelegate
import com.bonnetrouge.toonup.Delegates.DataFetchingDelegate
import com.bonnetrouge.toonup.Listeners.OnRVTransitionItemClicked
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import kotlinx.android.synthetic.main.fragment_browsing.*
import javax.inject.Inject

class BrowsingFragment @Inject constructor() : Fragment(), OnRVTransitionItemClicked {

    val browseViewModel by lazyAndroid { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }
    val bannerListAdapter by lazyAndroid { BannerListAdapter(this) }

    var dataFetchingDelegate: DataFetchingDelegate = CartoonFetchingDelegate()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_browsing, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        browsingRecyclerView.adapter = bannerListAdapter
        browsingRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        dataFetchingDelegate = (activity as BrowseActivity).stateMachine.getStateSafeBrowseDelegate()
        swipeRefreshLayout.setOnRefreshListener { refreshBanners() }
        if (bannerListAdapter.banners.size == 0) {
            refreshBanners()
        } else {
            showSearchIcon()
        }
        (activity as BrowseActivity).showBackButton()
    }

    fun refreshBanners() {
        if (bannerListAdapter.banners.size == 0) {
            dataFetchingDelegate.fetchBrowsingData({ showLoading() }, browseViewModel, {
                this.subscribe({
                    hideLoading()
                    hideErrorMsg()
                    swipeRefreshLayout.postDelayed({
                        bannerListAdapter.banners.addAll(it)
                        bannerListAdapter.notifyDataSetChanged()
                    }, 150)
                }, {
                    onNetworkError()
                })
            }, {
                onNetworkError()
            })
        } else {
            hideLoading()
        }
    }

    fun onNetworkError() {
        hideLoading()
        showErroMsg()
    }

    fun showErroMsg() {
        browsingErrorMessage?.visibility = View.VISIBLE
        hideSearchIcon()
    }

    fun hideErrorMsg() {
        browsingErrorMessage?.visibility = View.INVISIBLE
    }

    fun showLoading() {
        swipeRefreshLayout?.isRefreshing = true
        hideSearchIcon()
    }

    fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        showSearchIcon()
    }

    fun showSearchIcon() {
        ifAdded { (activity as BrowseActivity).showSearchIcon() }
    }

    fun hideSearchIcon() {
        ifAdded { (activity as BrowseActivity).hideSearchIcon() }
    }

    override fun onRVItemClicked(item: RVItem, imageView: ImageView) {
        (activity as BrowseActivity).navigateDetail(item as BasicSeriesInfo, imageView)
    }
}
