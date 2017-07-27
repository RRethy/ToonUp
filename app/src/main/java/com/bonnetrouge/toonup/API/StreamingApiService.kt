package com.bonnetrouge.toonup.API

import com.bonnetrouge.toonup.Model.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface StreamingApiService {

	@GET("/GetGenres")
	fun getGenres(): Observable<VideoGenres>

	@GET("/GetPopularCartoon")
	fun getPopularCartoons(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetPopularDubbed")
	fun getPopularAnime(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetPopularMovies")
	fun getPopularMovies(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetAllCartoon")
	fun getAllCartoons(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetAllDubbed")
	fun getAllAnime(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetAllMovies")
	fun getAllMovies(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetNewCartoon")
	fun getNewCartoons(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetNewDubbed")
	fun getNewAnime(): Observable<Collection<BasicSeriesInfo>>

	@GET("/GetNewMovies")
	fun getNewMovies(): Observable<Collection<BasicSeriesInfo>>
	
	@GET("/GetUpdates")
	fun getNewEpisodes(): Observable<NewEpisodes>

	@GET("/GetDetails/{seriesId}")
	fun getDetails(@Path("seriesId") id: Int): Observable<Series>

	@GET("/GetVideos/{episodeId}?direct")
	fun getDescriptiveStreamingUrls(@Path("episodeId") id: Int): Observable<Collection<Collection<DescriptiveStreamingUrl>>>

	@GET("/GetVideos/{episodeId}")
	fun getPlainStreamingUrls(@Path("episodeId") id: Int): Observable<Collection<Collection<String>>>
}