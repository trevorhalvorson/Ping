package com.trevorhalvorson.ping.injection.module

import com.trevorhalvorson.ping.builder.BuilderActivity
import com.trevorhalvorson.ping.builder.BuilderModule
import com.trevorhalvorson.ping.injection.scope.PerActivity
import com.trevorhalvorson.ping.sendMessage.SendMessageActivity
import com.trevorhalvorson.ping.sendMessage.SendMessageModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SendMessageModule::class))
    abstract fun sendMessageActivity(): SendMessageActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(BuilderModule::class))
    abstract fun builderActivity(): BuilderActivity

}