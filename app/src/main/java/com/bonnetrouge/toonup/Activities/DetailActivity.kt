package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import com.bonnetrouge.toonup.R
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.bonnetrouge.toonup.Commons.Ext.DTAG
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.DI.Modules.DetailActivityModule
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.Model.*
import com.bonnetrouge.toonup.UI.DetailsAdapter
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.DetailViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.DetailViewModelFactory
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

//TODO: Clean this shit up
class DetailActivity : BaseActivity(), OnRecyclerViewItemClicked {


	@Inject lateinit var detailViewModelFactory: DetailViewModelFactory
	lateinit var detailViewModel: DetailViewModel

	val detailAdapter by lazy { DetailsAdapter(this) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)
		app.component.plus(DetailActivityModule()).inject(this)
		detailViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(DetailViewModel::class.java)
		setupToolbar()
		setupRecyclerView()
		popularRecyclerView()
		fetchBackingData()
	}

	fun setupToolbar() {
		toolbar.title = intent.getStringExtra(TITLE)
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

	fun popularRecyclerView() {
		if (detailAdapter.items.isEmpty()) {
			detailViewModel.getFullSeriesInfo(intent.getStringExtra(DetailActivity.TITLE))
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe({
						detailAdapter.items.clear()
						if (it._embedded?.episodes != null) {
							detailAdapter.items.addAll(it._embedded.episodes)
							detailAdapter.notifyDataSetChanged()
						}
					}, {

					})
		}
	}

	fun fetchBackingData() {
		detailViewModel.getBasicDetails(intent.getStringExtra(DetailActivity.ID))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe {
					Log.d(DTAG, it.toString())
				}
	}

	fun hideError() {
		errorMessage.visibility = View.VISIBLE
	}

	fun showError() {
		errorMessage.visibility = View.GONE
	}

	override fun onRecyclerViewItemClicked(item: RVItem) {
		if (item is ExtendedEpisodeInfo) {
			val season = item.season?.toString() ?: "-1"
			val episode = item.number?.toString() ?: "-1"
			detailViewModel.basicSeriesDetails?.episode
					?.forEach {
						with (it.name.toLowerCase()) {
							if (contains("episode $episode")
									&& (contains("season $season") || (season == "1" && !contains("season")))
									|| (contains("episode ${detailAdapter.items.indexOf(item) + 1}") && !contains("season"))) {
								Log.d(DTAG, "${it.id} + ${it.name}")
								return
							}
						}
					}
		}
	}

	companion object {
		val ID = "Id"
		val TITLE = "Title"
		val DESCRIPTION = "Description"

		fun navigate(context: Context, basicSeriesInfo: BasicSeriesInfo) {
			val intent = Intent(context, DetailActivity::class.java)
			intent.putExtra(ID, basicSeriesInfo.id)
			intent.putExtra(TITLE, basicSeriesInfo.name)
			intent.putExtra(DESCRIPTION, basicSeriesInfo.description)
			context.startActivity(intent)
		}
	}
}
