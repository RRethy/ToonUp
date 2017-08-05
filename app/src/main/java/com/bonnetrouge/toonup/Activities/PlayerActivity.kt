package com.bonnetrouge.toonup.Activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bonnetrouge.toonup.Commons.Ext.app

import com.bonnetrouge.toonup.R
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

	var player: SimpleExoPlayer? = null

	companion object {

		const val VIDEO_ID = "Video ID"

		fun navigate(context: Context, id: String) {
			val intent = Intent(context, PlayerActivity::class.java)
			intent.putExtra(VIDEO_ID, id)
			context.startActivity(intent)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_player)
		player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
		exoPlayerView.player = player
		val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "ToonUp"))
		val extractorFactory = DefaultExtractorsFactory()

		val firstSource = ExtractorMediaSource(
				Uri.parse(intent.getStringExtra("this is not a url")),
				dataSourceFactory,
				extractorFactory,
				null,
				null)
		val videoSource = ConcatenatingMediaSource(firstSource)
		player?.prepare(videoSource)
		player?.playWhenReady = true
	}

	override fun onStop() {
		super.onStop()
		player?.release()
	}
}
