package com.bonnetrouge.toonup.UI

import android.support.v7.widget.GridLayoutManager
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth

class GridAutofitLayoutManager(columnWidthDp: Double) : GridLayoutManager(app, 1, GridLayoutManager.VERTICAL, false) {
    init { spanCount = Math.max(1, getDisplayWidth() / convertToPixels(columnWidthDp)) }
}
