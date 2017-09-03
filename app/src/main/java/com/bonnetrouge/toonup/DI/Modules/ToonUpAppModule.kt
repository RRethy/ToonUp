package com.bonnetrouge.toonup.DI.Modules

import com.bonnetrouge.toonup.API.StreamingApiService
import com.bonnetrouge.toonup.Commons.WackClasses.UrbanFitGenerator
import com.bonnetrouge.toonup.Commons.WackClasses.UrlGenerator
import com.bonnetrouge.toonup.Data.VideoRepository
import com.bonnetrouge.toonup.ToonUpApp
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ToonUpAppModule(val app: ToonUpApp) {
    @Provides
    @Singleton
    fun provideToonUpApp() = app

    @Provides
    @Singleton
    fun provideVideoRepository(streamingApiService: StreamingApiService) = VideoRepository(streamingApiService)

    @Provides
    @Singleton
    fun provideStreamingApiService(): StreamingApiService {
        val appVersion = "8.0"
        val okHttpClient = OkHttpClient.Builder().addInterceptor {
            it.proceed(it.request().newBuilder().addHeader("App-Version", appVersion).build())
        }.build()
        val retrofit = UrbanFitGenerator.generate(okHttpClient)

        return retrofit.create(StreamingApiService::class.java)
    }
}