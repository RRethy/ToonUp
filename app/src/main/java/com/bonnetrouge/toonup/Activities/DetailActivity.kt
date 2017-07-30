package com.bonnetrouge.toonup.Activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.bonnetrouge.toonup.R
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.EditText
import android.content.Intent
import android.os.Parcelable
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth
import com.bonnetrouge.toonup.Model.BasicSeriesInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_detail)
		toolbar.title = intent.getStringExtra("TITLE")
		setSupportActionBar(toolbar)
		fab.setOnClickListener {
			Snackbar.make(it, "SHIT FUCK SHITTY FUCKING SHIT", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show()
		}
		Picasso.with(this)
				.load(intent.getStringExtra("IMAGE_URL"))
				.resize(getDisplayWidth(), convertToPixels(240.0))
				.centerCrop()
				.into(parallaxImage)

	}

	companion object {
		fun navigate(context: Context, basicSeriesInfo: BasicSeriesInfo) {
			val intent = Intent(context, DetailActivity::class.java)
			intent.putExtra("IMAGE_URL", "http://www.animetoon.org/images/series/big/${basicSeriesInfo.id}.jpg")
			intent.putExtra("TITLE", basicSeriesInfo.name)
			context.startActivity(intent)
		}
	}
}
