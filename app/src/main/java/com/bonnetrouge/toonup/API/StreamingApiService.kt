package com.bonnetrouge.toonup.API

import com.bonnetrouge.toonup.Model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface StreamingApiService {

	@GET("/GetGenres")
	fun getGenres(): Single<VideoGenres>

	@GET("/GetPopularCartoon")
	fun getPopularCartoons(): Single<List<BasicSeriesInfo>>

	@GET("/GetPopularDubbed")
	fun getPopularAnime(): Single<List<BasicSeriesInfo>>

	@GET("/GetPopularMovies")
	fun getPopularMovies(): Single<List<BasicSeriesInfo>>

	@GET("/GetAllCartoon")
	fun getAllCartoons(): Observable<List<BasicSeriesInfo>>

	@GET("/GetAllDubbed")
	fun getAllAnime(): Single<List<BasicSeriesInfo>>

	@GET("/GetAllMovies")
	fun getAllMovies(): Single<List<BasicSeriesInfo>>

	@GET("/GetNewCartoon")
	fun getNewCartoons(): Single<List<BasicSeriesInfo>>

	@GET("/GetNewDubbed")
	fun getNewAnime(): Single<List<BasicSeriesInfo>>

	@GET("/GetNewMovies")
	fun getNewMovies(): Single<List<BasicSeriesInfo>>
	
	@GET("/GetUpdates")
	fun getNewEpisodes(): Single<NewEpisodes>

	@GET("/GetDetails/{seriesId}")
	fun getDetails(@Path("seriesId") id: String): Single<Series>

	@GET("/GetVideos/{episodeId}?direct")
	fun getDescriptiveStreamingUrls(@Path("episodeId") id: String): Single<List<List<DescriptiveStreamingUrl>>>

	@GET("/GetVideos/{episodeId}")
	fun getRawStreamingUrls(@Path("episodeId") id: String): Single<List<List<String>>>
}