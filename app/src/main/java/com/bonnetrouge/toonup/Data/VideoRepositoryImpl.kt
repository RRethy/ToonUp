package com.bonnetrouge.toonup.Data

import com.bonnetrouge.toonup.API.StreamingApiService

class VideoRepositoryImpl(private val streamingApiService: StreamingApiService) : VideoRepository {

    override fun getGenres() = streamingApiService.getGenres()

    override fun getPopularCartoons() = streamingApiService.getPopularCartoons()

    override fun getPopularAnime() = streamingApiService.getPopularAnime()

    override fun getPopularMovies() = streamingApiService.getPopularMovies()

    override fun getAllCartoons() = streamingApiService.getAllCartoons()

    override fun getAllAnime() = streamingApiService.getAllAnime()

    override fun getAllMovies() = streamingApiService.getAllMovies()

    override fun getNewCartoons() = streamingApiService.getNewCartoons()

    override fun getNewAnime() = streamingApiService.getNewAnime()

    override fun getNewMovies() = streamingApiService.getNewMovies()

    override fun getNewEpisodes() = streamingApiService.getNewEpisodes()

    override fun getDetails(id: String) = streamingApiService.getDetails(id)

    override fun getDescriptiveStreamingUrls(id: String) = streamingApiService.getDescriptiveStreamingUrls(id)

    override fun getRawStreamingUrls(id: String) = streamingApiService.getRawStreamingUrls(id)
}