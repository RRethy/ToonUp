package com.bonnetrouge.toonup.Fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonnetrouge.toonup.Activities.BrowseActivity
import com.bonnetrouge.toonup.Commons.Ext.resString
import com.bonnetrouge.toonup.Fragment.BaseFragment
import com.bonnetrouge.toonup.Model.BannerModel
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.VideoGenres
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.BannerListAdapter
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_browse_anime.*
import javax.inject.Inject

class BrowseAnimeFragment @Inject constructor(): BaseFragment() {

    val browseViewModel by lazy { ViewModelProviders.of(activity).get(BrowseViewModel::class.java) }
    val bannerListAdapter by lazy { BannerListAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_browse_anime, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        animeRecyclerView.adapter = bannerListAdapter
        animeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        swipeRefreshLayout.setOnRefreshListener { refreshBanners() }
        refreshBanners()
    }

    fun refreshBanners() {
        showLoading()
        browseViewModel.ensureGenresNotNull( { onGenresSuccess(it) }, { onGenresFailure() } )
    }

    fun onGenresSuccess(videoGenres: VideoGenres) {
        populateBanners(videoGenres)
    }

    fun onGenresFailure() {
        hideLoading()
        showErroMsg()
    }

    fun populateBanners(videoGenres: VideoGenres) {
        Observable.zip<MutableList<BannerModel>, BannerModel, MutableList<BannerModel>>(
                getAllMoviesObservable(videoGenres),
                getPopularMoviesObservable(),
                io.reactivex.functions.BiFunction {
                    allSeries, popularCartoons ->
                    val list = mutableListOf<BannerModel>()
                    list.add(popularCartoons)
                    list.addAll(allSeries)
                    list
                })
                .subscribe({
                    hideLoading()
                    hideErrorMsg()
                    swipeRefreshLayout.postDelayed({
                        bannerListAdapter.banners.addAll(it)
                        bannerListAdapter.notifyDataSetChanged()
                    }, 150)
                }, {
                    hideLoading()
                    showErroMsg()
                })
    }

    fun getAllMoviesObservable(videoGenres: VideoGenres) =
            browseViewModel.getAllAnime()
                    .retry(3)
                    .map {
                        val seriesByGenre = mutableListOf<BannerModel>()
                        for (videoGenre in videoGenres.genres) {
                            val seriesList = it.filter({ it.genres.contains(videoGenre) }).toMutableList()
                            seriesList.sortByDescending { it.rating }
                            if (seriesList.size > 0) seriesByGenre.add(BannerModel(videoGenre, seriesList))
                        }
                        seriesByGenre
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun getPopularMoviesObservable() =
            browseViewModel.getPopularAnime()
                    .retry(3)
                    .map {
                        BannerModel(resString(R.string.popular), it)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun showErroMsg() {
        errorMessage?.visibility = View.VISIBLE
    }

    fun hideErrorMsg() {
        errorMessage?.visibility = View.INVISIBLE
    }

    fun showLoading() {
        swipeRefreshLayout?.isRefreshing = true
    }

    fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun onRecyclerViewItemClicked(item: RVItem) {
        (activity as BrowseActivity).navigateDetail(item as BasicSeriesInfo)
    }
}
