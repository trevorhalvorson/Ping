package com.trevorhalvorson.ping.injection.module

import com.trevorhalvorson.ping.BuildConfig
import com.trevorhalvorson.ping.builder.BuilderApi
import com.trevorhalvorson.ping.injection.scope.PerApplication
import com.trevorhalvorson.ping.sendMessage.SendMessageApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    @PerApplication
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @PerApplication
    @Named("messagingService")
    fun provideMessagingService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.MESSAGING_URL_BASE)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @PerApplication
    @Provides
    internal fun provideSendMessageApi(@Named("messagingService") retrofit: Retrofit):
            SendMessageApi = retrofit.create(SendMessageApi::class.java)

    @Provides
    @PerApplication
    @Named("builderService")
    fun provideBuilderService(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BUILDER_URL_BASE)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @PerApplication
    @Provides
    internal fun provideBuilderApi(@Named("builderService") retrofit: Retrofit):
            BuilderApi = retrofit.create(BuilderApi::class.java)

}