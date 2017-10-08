package com.bonnetrouge.toonup.Model

import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes

data class FullSeriesInfo(val id: Long?,
                          val type: String?,
                          val language: String?,
                          val genres: List<String>?,
                          val premiered: String?,
                          val officialSite: String?,
                          val schedule: Schedule?,
                          val rating: Rating?,
                          val network: Network?,
                          val externals: Externals?,
                          val image: Image?,
                          val summary: String?,
                          val _embedded: Embedded?)

data class EpisodesExtendedInfo(val _embedded: Embedded?)

data class Embedded(val episodes: MutableList<ExtendedEpisodeInfo>)

data class ExtendedEpisodeInfo(var id: Long?,
                               var url: String?,
                               var name: String?,
                               var season: Int?,
                               var number: Int?,
                               var airdate: String?,
                               var airtime: String?,
                               var airstamp: String?,
                               var image: Image?,
                               var summary: String?) : RVItem {

    override fun getItemViewType() = RVItemViewTypes.EXTENDED_EPISODE
}

data class SeriesExtendedInfo(val id: Int?,
                              val type: String?,
                              val language: String?,
                              val genres: List<String>?,
                              val premiered: String?,
                              val officialSite: String?,
                              val schedule: Schedule?,
                              val rating: Rating?,
                              val network: Network?,
                              val externals: Externals?,
                              val image: Image?,
                              val summary: String?)

data class Schedule(val time: String?, val days: List<String>?)

data class Rating(val average: Double?)

data class Network(val name: String?, val country: Country?)

data class Country(val name: String?, val code: String?, val timezone: String?)

data class Externals(val tvrage: Int?, val thetvdb: Int?, val imdb: String?)

data class Image(val medium: String?, val original: String?)
