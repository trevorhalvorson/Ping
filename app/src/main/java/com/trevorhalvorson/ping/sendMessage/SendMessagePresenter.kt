package com.trevorhalvorson.ping.sendMessage

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

    override fun sendMessage(message: String) {
        TODO("not implemented")
    }

}