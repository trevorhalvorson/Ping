package com.trevorhalvorson.ping.injection

import android.app.Application
import com.trevorhalvorson.ping.PingApplication
import com.trevorhalvorson.ping.injection.module.ActivityBindingModule
import com.trevorhalvorson.ping.injection.module.ApplicationModule
import com.trevorhalvorson.ping.injection.module.NetworkModule
import com.trevorhalvorson.ping.injection.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(modules = arrayOf(ApplicationModule::class,
        ActivityBindingModule::class,
        AndroidSupportInjectionModule::class,
        NetworkModule::class))
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: PingApplication)

    override fun inject(instance: DaggerApplication?)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): ApplicationComponent.Builder

        fun build(): ApplicationComponent
    }
}