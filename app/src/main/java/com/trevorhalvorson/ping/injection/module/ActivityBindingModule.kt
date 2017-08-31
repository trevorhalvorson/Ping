package com.trevorhalvorson.ping.injection.module

import com.trevorhalvorson.ping.injection.scopes.PerActivity
import com.trevorhalvorson.ping.sendMessage.SendMessageActivity
import com.trevorhalvorson.ping.sendMessage.SendMessageModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SendMessageModule::class))
    abstract fun sendMessageActivity(): SendMessageActivity

}