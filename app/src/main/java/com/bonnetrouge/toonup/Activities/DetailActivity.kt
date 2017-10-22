package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.bonnetrouge.toonup.Adapters.DetailsAdapter
import com.bonnetrouge.toonup.Commons.Ext.*
import com.bonnetrouge.toonup.DI.Modules.DetailActivityModule
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.Episode
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.DetailViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.DetailViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : BaseActivity(), OnRecyclerViewItemClicked {

    @Inject lateinit var detailViewModelFactory: DetailViewModelFactory
    lateinit var detailViewModel: DetailViewModel

    val detailAdapter by lazyAndroid { DetailsAdapter(this) }
    val backgroundAnimation by lazyAndroid { rootBackground.background as AnimationDrawable }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        app.component.plus(DetailActivityModule()).inject(this)
        detailViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(DetailViewModel::class.java)
        cacheIntentData()
        setupToolbar()
        setupRecyclerView()
        backgroundAnimation.with {
            setEnterFadeDuration(5000)
            setExitFadeDuration(5000)
        }
        postDelayed(200) { popularRecyclerView() }
    }

    override fun onResume() {
        super.onResume()
        backgroundAnimation.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundAnimation.stop()
    }

    fun cacheIntentData() {
        detailViewModel.id = intent.getStringExtra(DetailActivity.ID)
        detailViewModel.title = intent.getStringExtra(DetailActivity.TITLE)
        detailViewModel.description = intent.getStringExtra(DetailActivity.DESCRIPTION)
        detailViewModel.released = intent.getStringExtra(DetailActivity.RELEASED)
        detailViewModel.genres = intent.getStringExtra(DetailActivity.GENRES)
        detailViewModel.rating = intent.getStringExtra(DetailActivity.RATING)
        detailViewModel.status = intent.getStringExtra(DetailActivity.STATUS)
    }

    fun setupToolbar() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this)
                .load("http://www.animetoon.org/images/series/big/${intent.getStringExtra(ID)}.jpg")
                .into(parallaxImage)
    }

    fun setupRecyclerView() {
        detailsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        detailsRecyclerView.adapter = detailAdapter
    }

    //TODO: Use coroutines instead of wack ass logic
    fun popularRecyclerView() {
        dog("1")
        detailViewModel.getMediaInfo()
                .subscribe({
                    dog("qumansub1")
                    detailAdapter.items.clear()
                    detailAdapter.items.addAll(it)
                    detailAdapter.notifyDataSetChanged()
                }, {
                    dog("qumanqumanfail")
                    //TODO: network failure handling
                })
    }

    fun hideError() {
        errorMessage.visibility = View.VISIBLE
    }

    fun showError() {
        errorMessage.visibility = View.GONE
    }

    override fun onRecyclerViewItemClicked(item: RVItem) {
        PlayerActivity.navigate(this, (item as Episode).id)
    }

    companion object {
        val ID = "Id"
        val TITLE = "Title"
        val DESCRIPTION = "Description"
        val RELEASED = "Released"
        val GENRES = "Genres"
        val RATING = "Ratings"
        val STATUS = "Status"

        fun navigate(activity: AppCompatActivity, basicSeriesInfo: BasicSeriesInfo, imageView: ImageView) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(ID, basicSeriesInfo.id)
            intent.putExtra(TITLE, basicSeriesInfo.name)
            intent.putExtra(DESCRIPTION, basicSeriesInfo.description)
            intent.putExtra(RELEASED, basicSeriesInfo.released)
            intent.putExtra(GENRES, basicSeriesInfo.genres.toString())
            intent.putExtra(RATING, basicSeriesInfo.rating.toString())
            intent.putExtra(STATUS, basicSeriesInfo.status)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, "DetailParallaxImage")
            activity.startActivity(intent, options.toBundle())
        }
    }
}
