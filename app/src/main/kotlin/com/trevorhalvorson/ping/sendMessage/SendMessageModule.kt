package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.injection.scope.PerActivity
import dagger.Module
import dagger.Provides

@Module
open class SendMessageModule {

    @PerActivity
    @Provides
    internal fun provideSendMessageView(view: SendMessageActivity): SendMessageContract.View {
        return view
    }

    @PerActivity
    @Provides
    internal fun provideSendMessagePresenter(view: SendMessageContract.View,
                                             api: SendMessageApi): SendMessageContract.Presenter {
        return SendMessagePresenter(view, api)
    }

}