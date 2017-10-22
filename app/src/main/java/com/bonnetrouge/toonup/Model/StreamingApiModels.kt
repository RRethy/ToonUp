package com.bonnetrouge.toonup.Model

import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes

data class BasicSeriesInfo(val id: String,
                           val name: String,
                           val description: String,
                           val status: String,
                           val released: String,
                           val rating: Float,
                           val genres: Collection<String>) : RVItem {
    override fun getItemViewType() = RVItemViewTypes.BASIC_SERIES
}

data class VideoGenres(val genres: List<String>)

data class Series(val name: String,
                  val episode: Collection<Episode>)

data class NewEpisode(val id: String,
                      val name: String,
                      val section: String,
                      val episodes: Collection<Episode>)

data class NewEpisodes(val updates: Collection<NewEpisode>)

data class Episode(val id: String,
                   var name: String,
                   val date: String) : RVItem {
    override fun getItemViewType() = RVItemViewTypes.EPISODE
}

data class DescriptiveStreamingUrl(val quality: String,
                                   val source: String,
                                   val filename: String,
                                   val link: String,
                                   val sub: String)
