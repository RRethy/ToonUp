package com.bonnetrouge.toonup.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiWrapper {
	private val okHttpClient = OkHttpClient.Builder().addInterceptor {
					it.proceed(it.request().newBuilder().addHeader("App-Version", "8.0").build())
				}.build()
	private val retrofit = Retrofit.Builder()
			.baseUrl("")
			.client(okHttpClient)
			.addConverterFactory(MoshiConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.build()
	val apiService = retrofit.create(StreamingApiService::class.java)
}