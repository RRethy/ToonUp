package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.bonnetrouge.toonup.R
import android.content.Intent
import android.util.Log
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth
import com.bonnetrouge.toonup.DI.Modules.DetailActivityModule
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.ViewModels.DetailViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.DetailViewModelFactory
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject


class DetailActivity : BaseActivity() {

	@Inject
	lateinit var detailViewModelFactory: DetailViewModelFactory
	lateinit var detailViewModel: DetailViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)
		app.component.plus(DetailActivityModule()).inject(this)
		detailViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(DetailViewModel::class.java)
		toolbar.title = intent.getStringExtra(TITLE)
		setSupportActionBar(toolbar)
		fab.setOnClickListener {
			Snackbar.make(it, "Added to your favorites!", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}
		Picasso.with(this)
				.load("http://www.animetoon.org/images/series/big/${intent.getStringExtra(ID)}.jpg")
				.resize(getDisplayWidth(), convertToPixels(240.0))
				.centerCrop()
				.into(parallaxImage)
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
}
