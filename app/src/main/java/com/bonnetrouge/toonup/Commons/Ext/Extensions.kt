package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.FragmentTransaction
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity

val app: ToonUpApp
    get() = ToonUpApp.app

inline fun BaseActivity.swapInFragment(name: String? = null, swapInfo: FragmentTransaction.() -> FragmentTransaction)
        = supportFragmentManager.beginTransaction().swapInfo().addToBackStack(name).commit()