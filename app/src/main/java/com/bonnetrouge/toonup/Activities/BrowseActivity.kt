package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.bonnetrouge.toonup.API.StreamingApiService
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.swapInFragment
import com.bonnetrouge.toonup.DI.Modules.BrowseModule
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.BrowseViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.BrowseViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : BaseActivity() {

	@Inject
	lateinit var browseViewModelFactory: BrowseViewModelFactory
	lateinit var browseViewModel: BrowseViewModel

	val component by lazy { app.component.plus(BrowseModule()) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_browse)
		component.inject(this)
		browseViewModel = ViewModelProviders.of(this, browseViewModelFactory).get(BrowseViewModel::class.java)
		setSupportActionBar(toolbar)
		swapInFragment {
			replace(fragmentContainer.id, Fragment()) // TODO: Add a fragment here
		}
		val apiService = StreamingApiService.create()
		apiService.getPopularCartoons()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.io())
				.subscribe ({
					Log.d("quman", it.toString())
				},{
					Log.d("quman: Failed: ", it.toString())
				})
	}
}
