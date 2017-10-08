package com.bonnetrouge.toonup.Model

data class NewEpisode(val id: String,
                      val name: String,
                      val section: String,
                      val episodes: Collection<Episode>)