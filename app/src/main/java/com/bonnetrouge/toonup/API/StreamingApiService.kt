package com.bonnetrouge.toonup.API

import com.bonnetrouge.toonup.Model.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface StreamingApiService {

	@GET("/GetGenres")
	fun getGenres(): Single<VideoGenres>

	@GET("/GetPopularCartoon")
	fun getPopularCartoons(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetPopularDubbed")
	fun getPopularAnime(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetPopularMovies")
	fun getPopularMovies(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetAllCartoon")
	fun getAllCartoons(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetAllDubbed")
	fun getAllAnime(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetAllMovies")
	fun getAllMovies(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetNewCartoon")
	fun getNewCartoons(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetNewDubbed")
	fun getNewAnime(): Single<Collection<BasicSeriesInfo>>

	@GET("/GetNewMovies")
	fun getNewMovies(): Single<Collection<BasicSeriesInfo>>
	
	@GET("/GetUpdates")
	fun getNewEpisodes(): Single<NewEpisodes>

	@GET("/GetDetails/{seriesId}")
	fun getDetails(@Path("seriesId") id: String): Single<Series>

	@GET("/GetVideos/{episodeId}?direct")
	fun getDescriptiveStreamingUrls(@Path("episodeId") id: Int): Single<Collection<Collection<DescriptiveStreamingUrl>>>

	@GET("/GetVideos/{episodeId}")
	fun getPlainStreamingUrls(@Path("episodeId") id: Int): Single<Collection<Collection<String>>>
}