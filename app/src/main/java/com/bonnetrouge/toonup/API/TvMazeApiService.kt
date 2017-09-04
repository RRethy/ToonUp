package com.bonnetrouge.toonup.API

import com.bonnetrouge.toonup.Model.FullSeriesInfo
import com.bonnetrouge.toonup.Model.SeriesExtendedInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeApiService {

	@GET("/singlesearch/shows")
	fun getExtendedSeriesInfo(@Query("q") seriesName: String): Observable<SeriesExtendedInfo>

	@GET("/singlesearch/shows?embed=episodes")
	fun getExtendedEpisodesInfo(@Query("q") seriesName: String): Observable<FullSeriesInfo>
}