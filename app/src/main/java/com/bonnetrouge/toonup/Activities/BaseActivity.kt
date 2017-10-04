package com.bonnetrouge.toonup.Activities

import android.support.v7.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
	override fun onSupportNavigateUp(): Boolean {
		onBackPressed()
		return true
	}

	fun showBackButton() {
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	fun hideBackButton() {
		supportActionBar?.setDisplayHomeAsUpEnabled(false)
	}
}