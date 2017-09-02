package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.bonnetrouge.toonup.R
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.DI.Modules.DetailActivityModule
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.Model.Episode
import com.bonnetrouge.toonup.UI.DetailsAdapter
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.DetailViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.DetailViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject


class DetailActivity : BaseActivity(), OnRecyclerViewItemClicked {


	@Inject
	lateinit var detailViewModelFactory: DetailViewModelFactory
	lateinit var detailViewModel: DetailViewModel
	val detailAdapter by lazy { DetailsAdapter(this) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)
		app.component.plus(DetailActivityModule()).inject(this)
		detailViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(DetailViewModel::class.java)
		toolbar.title = intent.getStringExtra(TITLE)
		setSupportActionBar(toolbar)
		fab.setOnClickListener {

		}
		Glide.with(this)
				.load("http://www.animetoon.org/images/series/big/${intent.getStringExtra(ID)}.jpg")
				.apply(RequestOptions.centerCropTransform())
				.into(parallaxImage)
		detailsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
		detailsRecyclerView.adapter = detailAdapter
		popularRecyclerView()
	}

	fun popularRecyclerView() {
		if (detailAdapter.itemList.isEmpty()) {
			detailViewModel.getDetailsObservable(intent.getStringExtra(DetailActivity.ID))
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe({
						detailAdapter.itemList.addAll(it.episode)
						detailAdapter.notifyDataSetChanged()
					}, {
						Toast.makeText(this, "Shit fucked up", Toast.LENGTH_LONG).show()
					})
		}
	}

	companion object {
		val ID = "Id"
		val TITLE = "Title"

		fun navigate(context: Context, basicSeriesInfo: BasicSeriesInfo) {
			val intent = Intent(context, DetailActivity::class.java)
			intent.putExtra(ID, basicSeriesInfo.id)
			intent.putExtra(TITLE, basicSeriesInfo.name)
			context.startActivity(intent)
		}
	}

	override fun onRecyclerViewItemClicked(item: RVItem) {
		PlayerActivity.navigate(this, (item as Episode).id)
	}
}
