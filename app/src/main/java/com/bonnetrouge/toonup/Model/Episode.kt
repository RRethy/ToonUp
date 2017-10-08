package com.bonnetrouge.toonup.Model

import com.bonnetrouge.toonup.UI.RVItem
import com.bonnetrouge.toonup.UI.RVItemViewTypes

data class Episode(val id: String,
                   var name: String,
                   val date: String) : RVItem {
    override fun getItemViewType() = RVItemViewTypes.EPISODE
}