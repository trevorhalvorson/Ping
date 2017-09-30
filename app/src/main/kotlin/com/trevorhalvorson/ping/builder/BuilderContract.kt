package com.trevorhalvorson.ping.builder

import com.trevorhalvorson.ping.BasePresenter
import com.trevorhalvorson.ping.BaseView

interface BuilderContract {

    interface View : BaseView<Presenter> {

        fun showProgress()

        fun hideProgress()

        fun showError(error: String?)

        fun hideError()

    }

    interface Presenter : BasePresenter {

        fun startBuilder(config: BuilderConfig)

    }

}