package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.BasePresenter
import com.trevorhalvorson.ping.BaseView

interface SendMessageContract {

    interface View : BaseView<Presenter> {

        fun showProgress()

        fun hideProgress()

        fun showError()

        fun hideError()

        fun showInputState(valid: Boolean)

        fun clearInput()

    }

    interface Presenter : BasePresenter {

        fun updateInputState(input: CharSequence?)

        fun sendMessage()

    }
}