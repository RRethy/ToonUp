package com.bonnetrouge.toonup.Data

import com.bonnetrouge.toonup.Model.*
import io.reactivex.Observable

interface VideoRepository {
    fun getGenres(): Observable<VideoGenres>

    fun getPopularCartoons(): Observable<List<BasicSeriesInfo>>

    fun getPopularAnime(): Observable<List<BasicSeriesInfo>>

    fun getPopularMovies(): Observable<List<BasicSeriesInfo>>

    fun getAllCartoons(): Observable<List<BasicSeriesInfo>>

    fun getAllAnime(): Observable<List<BasicSeriesInfo>>

    fun getAllMovies(): Observable<List<BasicSeriesInfo>>

    fun getNewCartoons(): Observable<List<BasicSeriesInfo>>

    fun getNewAnime(): Observable<List<BasicSeriesInfo>>

    fun getNewMovies(): Observable<List<BasicSeriesInfo>>

    fun getNewEpisodes(): Observable<NewEpisodes>

    fun getDetails(id: String): Observable<Series>

    fun getDescriptiveStreamingUrls(id: String): Observable<List<List<DescriptiveStreamingUrl>>>

    fun getRawStreamingUrls(id: String): Observable<List<List<String>>>
}