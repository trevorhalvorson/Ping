package com.trevorhalvorson.ping.injection.module

import com.trevorhalvorson.ping.BuildConfig
import com.trevorhalvorson.ping.injection.scopes.PerApplication
import com.trevorhalvorson.ping.sendMessage.SendMessageApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @PerApplication
    @Provides
    internal fun provideSendMessageApi(retrofit: Retrofit):
            SendMessageApi = retrofit.create(SendMessageApi::class.java)
}