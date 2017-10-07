package com.bonnetrouge.toonup.Commons.Ext

import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

inline fun AppCompatActivity.fragmentTransaction(addToBackStack: Boolean = true, tag: String? = null, swapInfo: FragmentTransaction.() -> FragmentTransaction) {
    if (addToBackStack) supportFragmentManager.beginTransaction().swapInfo().addToBackStack(tag).commit()
    else supportFragmentManager.beginTransaction().swapInfo().commit()
}


