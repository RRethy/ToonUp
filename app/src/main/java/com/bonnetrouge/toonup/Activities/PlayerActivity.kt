package com.bonnetrouge.toonup.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.bonnetrouge.toonup.R

class PlayerActivity : AppCompatActivity() {

	companion object {
		fun navigate(context: Context) {
			val intent = Intent(context, PlayerActivity::class.java)
			context.startActivity(intent)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_player)
	}
}
