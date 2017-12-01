package com.bonnetrouge.toonup.Activities

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bonnetrouge.toonup.Commons.Ext.*
import com.bonnetrouge.toonup.DI.Modules.PlayerActivityModule
import com.bonnetrouge.toonup.Handlers.VideoLinkHandler
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.Model.DescriptiveStreamingUrl
import com.bonnetrouge.toonup.Model.LinkModelHolder
import com.bonnetrouge.toonup.Model.Series
import com.bonnetrouge.toonup.R
import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.ViewModels.PlayerViewModel
import com.bonnetrouge.toonup.ViewModels.ViewModelFactories.PlayerViewModelFactory
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_player.*
import javax.inject.Inject

class PlayerActivity : BaseActivity(), Player.EventListener {

    @Inject lateinit var playerViewModelFactory: PlayerViewModelFactory
    @Inject lateinit var videoLinkHandler: VideoLinkHandler
    lateinit var playerViewModel: PlayerViewModel
    lateinit var trackSelector: DefaultTrackSelector
    lateinit var dataSourceFactory: DefaultDataSourceFactory
    lateinit var extractorFactory: DefaultExtractorsFactory
    lateinit var mediaSource: ExtractorMediaSource
    var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setFullscreen()
        keepScreenOn()
        app.component.plus(PlayerActivityModule()).inject(this)
        playerViewModel = ViewModelProviders.of(this, playerViewModelFactory).get(PlayerViewModel::class.java)
        setupVideoLinkHandler()
        setupVideoPlayer(intent.getStringExtra(VIDEO_ID))
        cacheFutureEpisodeIds(intent.getStringExtra(EPISDOES_ID))
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        player?.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.playWhenReady = false
        player?.release()
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        with (playerProgressBar) {
            when (playbackState) {
                Player.STATE_IDLE -> visible()
                Player.STATE_BUFFERING -> visible()
                Player.STATE_READY -> invisible()
                Player.STATE_ENDED -> onMediaEnded()
            }
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) { }

    override fun onPlayerError(error: ExoPlaybackException?) {
        dog("onPlayerError")
        videoLinkHandler.onLinkError()
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) { }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) { }

    override fun onPositionDiscontinuity() { }

    override fun onRepeatModeChanged(repeatMode: Int) { }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) { }

    private fun setFullscreen() {
        val decorView = window.decorView
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun setupVideoLinkHandler() {
        videoLinkHandler.viewModel = playerViewModel
        videoLinkHandler.onNewLink = { link -> prepareNewLink(link) }
        videoLinkHandler.messageDispatcher = { msgRes, i -> showMessage(msgRes, i) }
    }

    private fun keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun setupVideoPlayer(id: String) {
        val streamingUrlsObservable = getStreamingUrls(id)
        trackSelector = DefaultTrackSelector()
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        player?.addListener(this)
        trackSelector.parameters
        exoPlayerView.player = player
        dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "ToonUp"))
        extractorFactory = DefaultExtractorsFactory()
        streamingUrlsObservable?.observeOn(AndroidSchedulers.mainThread())?.subscribe(
                {
                    findInitialUrl(it)
                }, {})
    }

    private fun cacheFutureEpisodeIds(ids: String) {
        playerViewModel.cacheSeriesIds(ids)
    }

    private fun getStreamingUrls(id: String): Observable<List<List<DescriptiveStreamingUrl>>>? {
        return playerViewModel.getFullStreamingUrls(id)
                .subscribeOn(Schedulers.io())
    }

    private fun findInitialUrl(streamingUrls: List<List<DescriptiveStreamingUrl>?>) {
        for (streamingUrlObject in streamingUrls) {
            if (streamingUrlObject != null) {
                prepareStreamUrl(streamingUrlObject[0].link)
                break
            }
        }
    }

    private fun prepareStreamUrl(url: String) {
        mediaSource = ExtractorMediaSource(
                Uri.parse(url),
                dataSourceFactory,
                extractorFactory,
                null,
                null
        )
        player?.prepare(mediaSource)
        player?.playWhenReady = true
    }

    private fun onMediaEnded() {
        if (playerViewModel.isMultiPartMedia()) {
            videoLinkHandler.onPartEnded()
        } else {
            val nextId = playerViewModel.getNextId()
            if (nextId == "") {
                onBackPressed()
            } else {
                setupVideoPlayer(nextId)
            }
        }
    }

    private fun prepareNewLink(link: String) {
        if (link == "") {
            onMediaEnded()
        } else {
            prepareStreamUrl(link)
        }
    }

    private fun showMessage(msgRes: Int, i: Int) {
        shortToast("${resString(msgRes)} ${i.toString()}")
    }

    companion object {

        const val VIDEO_ID = "Video ID"
        const val EPISDOES_ID = "Episodes ID"

        fun navigate(context: Context, id: String, series: Series) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(VIDEO_ID, id)
            intent.putExtra(EPISDOES_ID, series.episode.map { it.id }.toString())
            context.startActivity(intent)
        }
    }
}
