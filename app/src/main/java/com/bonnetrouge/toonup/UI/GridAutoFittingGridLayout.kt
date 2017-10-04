package com.bonnetrouge.toonup.UI

import android.support.v7.widget.GridLayoutManager
import com.bonnetrouge.toonup.Commons.Ext.app
import com.bonnetrouge.toonup.Commons.Ext.convertToPixels
import com.bonnetrouge.toonup.Commons.Ext.getDisplayWidth

class GridAutofitLayoutManager(columnWidthDp: Double) :
        GridLayoutManager(app,
                Math.max(1, convertToPixels((getDisplayWidth() / convertToPixels(columnWidthDp))).toInt()),
                GridLayoutManager.VERTICAL,
                false)
