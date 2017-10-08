package com.bonnetrouge.toonup.API

import com.bonnetrouge.toonup.Model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface StreamingApiService {

    @GET("/GetGenres")
    fun getGenres(): Observable<VideoGenres>

    @GET("/GetPopularCartoon")
    fun getPopularCartoons(): Observable<List<BasicSeriesInfo>>

    @GET("/GetPopularDubbed")
    fun getPopularAnime(): Observable<List<BasicSeriesInfo>>

    @GET("/GetPopularMovies")
    fun getPopularMovies(): Observable<List<BasicSeriesInfo>>

    @GET("/GetAllCartoon")
    fun getAllCartoons(): Observable<List<BasicSeriesInfo>>

    @GET("/GetAllDubbed")
    fun getAllAnime(): Observable<List<BasicSeriesInfo>>

    @GET("/GetAllMovies")
    fun getAllMovies(): Observable<List<BasicSeriesInfo>>

    @GET("/GetNewCartoon")
    fun getNewCartoons(): Observable<List<BasicSeriesInfo>>

    @GET("/GetNewDubbed")
    fun getNewAnime(): Observable<List<BasicSeriesInfo>>

    @GET("/GetNewMovies")
    fun getNewMovies(): Observable<List<BasicSeriesInfo>>

    @GET("/GetUpdates")
    fun getNewEpisodes(): Observable<NewEpisodes>

    @GET("/GetDetails/{seriesId}")
    fun getDetails(@Path("seriesId") id: String): Observable<Series>

    @GET("/GetVideos/{episodeId}?direct")
    fun getDescriptiveStreamingUrls(@Path("episodeId") id: String): Observable<List<List<DescriptiveStreamingUrl>>>

    @GET("/GetVideos/{episodeId}")
    fun getRawStreamingUrls(@Path("episodeId") id: String): Observable<List<List<String>>>
}