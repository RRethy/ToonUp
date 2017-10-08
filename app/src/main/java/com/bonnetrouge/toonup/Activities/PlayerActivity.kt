package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.dog
import com.bonnetrouge.toonup.DI.Modules.PlayerActivityModule
import com.bonnetrouge.toonup.Model.DescriptiveStreamingUrl
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.ViewModels.PlayerViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.PlayerViewModelFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_player.*
import javax.inject.Inject

class PlayerActivity : BaseActivity() {

    var player: SimpleExoPlayer? = null
    @Inject lateinit var playerViewModelFactory: PlayerViewModelFactory
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
        makeFullscreen()
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

    fun makeFullscreen() {
        val decorView = window.decorView
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    fun setupVideoPlayer(streamingUrlsObservable: Observable<List<List<DescriptiveStreamingUrl>>>?) {
        trackSelector = DefaultTrackSelector()
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        trackSelector.parameters
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
                dynamicMediaSource.addMediaSource(mediaSource)
            } else {
                dog("Api fuckup when getting streaming link")
            }
        }
        player?.prepare(dynamicMediaSource)
        player?.playWhenReady = true
    }
}
