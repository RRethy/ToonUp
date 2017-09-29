package com.bonnetrouge.toonup.Listeners

import android.widget.ImageView
import com.bonnetrouge.toonup.UI.RVItem

interface OnRVTransitionItemClicked {
    fun onRVItemClicked(item: RVItem, imageView: ImageView)
}