package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.DI.Modules.PlayerActivityModule
import com.bonnetrouge.toonup.Model.DescriptiveStreamingUrl

import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.PlayerViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.PlayerViewModelFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_player.*
import javax.inject.Inject

class PlayerActivity : BaseActivity() {

	var player: SimpleExoPlayer? = null
	@Inject
	lateinit var playerViewModelFactory: PlayerViewModelFactory
	lateinit var playerViewModel: PlayerViewModel

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
		app.component.plus(PlayerActivityModule()).inject(this)
		playerViewModel = ViewModelProviders.of(this, playerViewModelFactory).get(PlayerViewModel::class.java)
		setupVideoPlayer(getStreamingUrls(intent.getStringExtra(VIDEO_ID)))
	}

	override fun onStop() {
		super.onStop()
	}

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

	fun setupVideoPlayer(streamingUrlsObservable: Observable<List<List<DescriptiveStreamingUrl>>>?) {
		streamingUrlsObservable?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
				{
					configExoplayer(it)
				})
	}

	fun getStreamingUrls(id: String): Observable<List<List<DescriptiveStreamingUrl>>>? {
		return playerViewModel.getFullStreamingUrls(id)
				.subscribeOn(Schedulers.io())

	}

	fun configExoplayer(streamingUrls: List<List<DescriptiveStreamingUrl>>) {
		player = ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
		exoPlayerView.player = player
		val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "ToonUp"))
		val extractorFactory = DefaultExtractorsFactory()

		val firstSource = ExtractorMediaSource(
				Uri.parse(streamingUrls[0][0].link),
				dataSourceFactory,
				extractorFactory,
				null,
				null)
		val videoSource = ConcatenatingMediaSource(firstSource)
		player?.prepare(videoSource)
		player?.playWhenReady = true
	}
}
