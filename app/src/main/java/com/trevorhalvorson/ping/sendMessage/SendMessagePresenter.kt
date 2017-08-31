package com.trevorhalvorson.ping.sendMessage

import android.util.Patterns
import javax.inject.Inject

class SendMessagePresenter @Inject constructor(private val sendMessageView: SendMessageContract.View) : SendMessageContract.Presenter {

    init {
        sendMessageView.setPresenter(this)
    }

    override fun start() {
        TODO("not implemented")
    }

    override fun stop() {
        TODO("not implemented")
    }

    override fun updateInputState(input: CharSequence?) {
        sendMessageView.showInputState(Patterns.PHONE.matcher(input).matches())
    }

    override fun sendMessage() {
        TODO("not implemented")
    }

}