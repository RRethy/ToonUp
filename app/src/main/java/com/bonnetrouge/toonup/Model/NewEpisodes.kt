package com.bonnetrouge.toonup.Model

data class NewEpisodes(val updates: String) {

	data class UpdatedSeriesInfo(val id: String,
								 val name: String,
								 val section: String,
								 val episodes: Collection<Episode>)
}