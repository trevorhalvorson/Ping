package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.BasePresenter
import com.trevorhalvorson.ping.BaseView

interface SendMessageContract {

    interface View : BaseView<Presenter> {

        fun showProgress()

        fun hideProgress()

        fun showError()

        fun hideError()

        fun clearInput()

    }

    interface Presenter : BasePresenter {

        fun sendMessage(message: String)

    }
}