package com.trevorhalvorson.ping.injection.module

import android.app.Application
import android.content.Context
import com.trevorhalvorson.ping.injection.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

}