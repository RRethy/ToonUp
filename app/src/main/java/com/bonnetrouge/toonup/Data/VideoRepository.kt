package com.bonnetrouge.toonup.Data

import com.bonnetrouge.toonup.API.StreamingApiService

class VideoRepository(private val apiService: StreamingApiService) {

	fun getPopularCartoons() = apiService.getPopularCartoons()

	fun getAllCartoons() = apiService.getAllCartoons()

	fun getPopularMovies() = apiService.getPopularMovies()

	fun getDetails(seriesId: String) = apiService.getDetails(seriesId)

	fun getFullStreamingUrls(episodeId: String) = apiService.getDescriptiveStreamingUrls(episodeId)

	fun getRawStreamingUrls(episodeId: String) = apiService.getRawStreamingUrls(episodeId)
}