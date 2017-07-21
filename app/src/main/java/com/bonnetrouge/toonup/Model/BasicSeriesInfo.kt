package com.bonnetrouge.toonup.Model

data class BasicSeriesInfo(val id: String,
						   val name: String,
						   val description: String,
						   val status: String,
						   val released: String,
						   val rating: Float,
						   val genres: Collection<String>)