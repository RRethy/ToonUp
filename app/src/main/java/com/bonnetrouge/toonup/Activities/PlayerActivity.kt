package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.DI.Modules.PlayerActivityModule
import com.bonnetrouge.toonup.Model.DescriptiveStreamingUrl

import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.PlayerViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.PlayerViewModelFactory
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
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
	lateinit var trackSelector: DefaultTrackSelector
    lateinit var dataSourceFactory: DefaultDataSourceFactory
    lateinit var extractorFactory: DefaultExtractorsFactory
    lateinit var dynamicMediaSource: DynamicConcatenatingMediaSource

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
        player?.playWhenReady = false
	}

    override fun onStart() {
        super.onStart()
		player?.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }

    fun test() {
        dog("jj")
    }

	fun setupVideoPlayer(streamingUrlsObservable: Observable<List<List<DescriptiveStreamingUrl>>>?) {
        trackSelector = DefaultTrackSelector()
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        trackSelector.parameters
        player?.addListener(object : Player.EventListener {
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                dog("quman")
                test()
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            }

            override fun onLoadingChanged(isLoading: Boolean) {
            }

            override fun onPositionDiscontinuity() {
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
            }

        })
        exoPlayerView.player = player
        dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "ToonUp"))
        extractorFactory = DefaultExtractorsFactory()
		streamingUrlsObservable?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
				{
					configExoplayer(it)
				})
	}

	fun getStreamingUrls(id: String): Observable<List<List<DescriptiveStreamingUrl>>>? {
		return playerViewModel.getFullStreamingUrls(id)
				.subscribeOn(Schedulers.io())
	}

	fun configExoplayer(streamingUrls: List<List<DescriptiveStreamingUrl>?>) {
        dynamicMediaSource = DynamicConcatenatingMediaSource()
		for (streamingUrlObject in streamingUrls) {
			if (streamingUrlObject != null) {
                val mediaSource = ExtractorMediaSource(
                        Uri.parse(streamingUrlObject[0].link),
                        dataSourceFactory,
                        extractorFactory,
                        null,
                        null
                )
                val mediaSource3 = ExtractorMediaSource(
                        Uri.parse(streamingUrlObject[0].link),
                        dataSourceFactory,
                        extractorFactory,
                        null,
                        null
                )
                val mediaSource2 = ExtractorMediaSource(
                        Uri.parse("a"),
                        dataSourceFactory,
                        extractorFactory,
                        null,
                        null
                )
                dynamicMediaSource.addMediaSource(mediaSource)
                dynamicMediaSource.addMediaSource(mediaSource3)
                dynamicMediaSource.addMediaSource(mediaSource2)
            } else {
                dog("Api fuckup when getting streaming link")
            }
        }
		player?.prepare(dynamicMediaSource)
		player?.playWhenReady = true
	}
}
