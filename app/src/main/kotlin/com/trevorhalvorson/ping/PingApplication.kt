package com.trevorhalvorson.ping

import com.trevorhalvorson.ping.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PingApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerApplicationComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

}