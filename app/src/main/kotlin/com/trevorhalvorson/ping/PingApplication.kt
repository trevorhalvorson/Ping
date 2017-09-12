package com.trevorhalvorson.ping

import com.google.android.libraries.remixer.Remixer
import com.google.android.libraries.remixer.storage.LocalStorage
import com.google.android.libraries.remixer.ui.RemixerInitialization
import com.trevorhalvorson.ping.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PingApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerApplicationComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        RemixerInitialization.initRemixer(this)
        Remixer.getInstance().synchronizationMechanism = LocalStorage(applicationContext)
    }

}