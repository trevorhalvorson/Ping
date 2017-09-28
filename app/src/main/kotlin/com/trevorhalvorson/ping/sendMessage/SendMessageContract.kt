package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.BasePresenter
import com.trevorhalvorson.ping.BaseView

interface SendMessageContract {

    interface View : BaseView<Presenter> {

        fun showProgress()

        fun hideProgress()

        fun showError(error: String?)

        fun hideError()

        fun clearInput()

        fun showPinError()

        fun showAdminView()

        fun showConfigView()

    }

    interface Presenter : BasePresenter {

        fun sendMessage(phoneNumber: String, message: String)

        fun submitPin(pin: String): Boolean

    }
}