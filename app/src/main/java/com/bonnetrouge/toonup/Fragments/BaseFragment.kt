package com.bonnetrouge.toonup.Fragment

import android.support.v4.app.Fragment
import com.bonnetrouge.toonup.Listeners.OnRecyclerViewItemClicked
import com.bonnetrouge.toonup.UI.RVItem

abstract class BaseFragment : Fragment(), OnRecyclerViewItemClicked {
	override abstract fun onRecyclerViewItemClicked(item: RVItem)
}

