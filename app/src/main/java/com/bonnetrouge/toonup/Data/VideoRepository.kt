package com.bonnetrouge.toonup.Data

import com.bonnetrouge.toonup.API.StreamingApiService
import com.bonnetrouge.toonup.API.TvMazeApiService

class VideoRepository(private val streamingApiService: StreamingApiService, private val tvInfoApiService: TvMazeApiService) {

    fun getGenres() = streamingApiService.getGenres()

    fun getPopularCartoons() = streamingApiService.getPopularCartoons()

    fun getAllCartoons() = streamingApiService.getAllCartoons()

    fun getPopularMovies() = streamingApiService.getPopularMovies()

    fun getAllMovies() = streamingApiService.getAllMovies()

    fun getPopularAnime() = streamingApiService.getPopularAnime()

    fun getAllAnime() = streamingApiService.getAllAnime()

    fun getDetails(seriesId: String) = streamingApiService.getDetails(seriesId)

    fun getFullStreamingUrls(episodeId: String) = streamingApiService.getDescriptiveStreamingUrls(episodeId)

    fun getRawStreamingUrls(episodeId: String) = streamingApiService.getRawStreamingUrls(episodeId)


    fun getExtendedSeriesInfo(seriesName: String) = tvInfoApiService.getExtendedSeriesInfo(seriesName)

    fun getExtendedEpisodesInfo(seriesName: String) = tvInfoApiService.getExtendedEpisodesInfo(seriesName)
}