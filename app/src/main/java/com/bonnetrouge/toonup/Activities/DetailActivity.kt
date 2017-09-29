package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.bonnetrouge.toonup.R
import android.content.Intent
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bonnetrouge.toonup.Commons.Ext.DTAG
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.Commons.Ext.postDelayed
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
	}

	override fun onEnterAnimationComplete() {
        postDelayed(200) {
			popularRecyclerView()
			fetchBackingData()
			toolbar.title = intent.getStringExtra(DetailActivity.TITLE)
		}
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
		if (detailAdapter.items.isEmpty()) {
			populateFromMaze()
		}
	}

	fun populateFromMaze() {
		detailViewModel.getFullSeriesInfo(intent.getStringExtra(DetailActivity.TITLE))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
					detailAdapter.items.clear()
					if (it._embedded?.episodes != null) {
						detailAdapter.items.addAll(it._embedded.episodes)
						detailAdapter.notifyDataSetChanged()
					}
					fetchBackingData()
				}, {
					populateFromToon()
				})
	}

	fun populateFromToon() {
		detailViewModel.getBasicDetails(intent.getStringExtra(DetailActivity.ID))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe {
					detailAdapter.items.clear()
					detailAdapter.items.addAll(it.episode)
					detailAdapter.notifyDataSetChanged()
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

	fun transformIntoId(item: ExtendedEpisodeInfo): String {
		dog("Item clicked: $item")
		val season = item.season?.toString() ?: "-1"
		val episode = item.number?.toString() ?: "-1"
		detailViewModel.basicSeriesDetails?.episode
				?.forEach {
					with (it.name.toLowerCase()) {
						if (contains("episode $episode")
								&& (contains("season $season") || (season == "1" && !contains("season")))
								|| (contains("episode ${detailAdapter.items.indexOf(item) + 1}") && !contains("season"))) {
							return it.id
						}
					}
				}
		return ""
	}

	override fun onRecyclerViewItemClicked(item: RVItem) {
		val id: String
		if (item is ExtendedEpisodeInfo) {
			id = transformIntoId(item)
			if (id.isNotEmpty()) PlayerActivity.navigate(this, id)
			else Toast.makeText(this, "Dun work", Toast.LENGTH_LONG).show()
		} else if (item is Episode) {
			id = item.id
			PlayerActivity.navigate(this, id)
		}
	}

	companion object {
		val ID = "Id"
		val TITLE = "Title"
		val DESCRIPTION = "Description"

		fun navigate(activity: AppCompatActivity, basicSeriesInfo: BasicSeriesInfo, imageView: ImageView) {
			val intent = Intent(activity, DetailActivity::class.java)
			intent.putExtra(ID, basicSeriesInfo.id)
			intent.putExtra(TITLE, basicSeriesInfo.name)
			intent.putExtra(DESCRIPTION, basicSeriesInfo.description)
			val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, "DetailParallaxImage")
			activity.startActivity(intent, options.toBundle())
		}
	}
}
