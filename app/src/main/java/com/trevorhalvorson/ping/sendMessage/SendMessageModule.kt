package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.injection.scopes.PerActivity
import dagger.Module
import dagger.Provides

@Module
open class SendMessageModule {

    @PerActivity
    @Provides
    internal fun provideSendMessageView(sendMessageActivity: SendMessageActivity):
            SendMessageContract.View {
        return sendMessageActivity
    }

    @PerActivity
    @Provides
    internal fun provideSendMessagePresenter(sendMessageView: SendMessageContract.View):
            SendMessageContract.Presenter {
        return SendMessagePresenter(sendMessageView)
    }
}