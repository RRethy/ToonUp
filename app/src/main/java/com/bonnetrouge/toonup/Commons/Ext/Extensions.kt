package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.FragmentTransaction
import com.bonnetrouge.toonup.ToonUpApp
import com.bonnetrouge.toonup.Activities.BaseActivity

val app: ToonUpApp
    get() = ToonUpApp.app

inline fun BaseActivity.fragmentTransaction(swapInfo: FragmentTransaction.() -> FragmentTransaction)
        = supportFragmentManager.beginTransaction().swapInfo().commit()