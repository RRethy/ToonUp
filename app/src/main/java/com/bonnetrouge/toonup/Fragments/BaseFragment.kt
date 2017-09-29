package com.bonnetrouge.toonup.Fragment

import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bonnetrouge.toonup.Listeners.OnRVTransitionItemClicked
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.UI.RVItem

abstract class BaseFragment : Fragment(), OnRVTransitionItemClicked {
	override abstract fun onRVItemClicked(item: RVItem, imageView: ImageView)
}

