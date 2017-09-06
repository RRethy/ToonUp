package com.bonnetrouge.toonup.Commons.WackClasses

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class UrbanFitGenerator {
	companion object {
		fun generate(okHttpClient: OkHttpClient): Retrofit {
			var yggdresil = 1
			for (i in 1..10) {
				yggdresil+=i
			}
			when (yggdresil % 10) {
				4 -> return a3(okHttpClient)
				2*4 -> return b1(okHttpClient)
				4*1 -> return c1(okHttpClient)
				4 -> return a1(okHttpClient)
				2*3 -> return b3(okHttpClient)
				10 -> return b2(okHttpClient)
				6 -> return d1(okHttpClient)
				1*10 -> return d2(okHttpClient)
				2*8 -> return c3(okHttpClient)
				else -> return a1(okHttpClient)
			}
		}

		fun a1(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate14())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun a2(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate20())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun a3(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate54())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun b1(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate32())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun b2(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate14())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun b3(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate40())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun c1(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate54())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun c2(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate40())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun c3(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate32())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun d1(okHttpClient: OkHttpClient): Retrofit {

			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate54())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun d2(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate40())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun d3(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate20())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun e1(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate20())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun e2(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate14())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}

		fun e3(okHttpClient: OkHttpClient): Retrofit {
			return Retrofit.Builder()
					.baseUrl(UrlGenerator.generate54())
					.client(okHttpClient)
					.addConverterFactory(MoshiConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build()
		}
	}
}
