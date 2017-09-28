package com.trevorhalvorson.ping.sendMessage

import com.trevorhalvorson.ping.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SendMessagePresenter @Inject constructor(
        private val view: SendMessageContract.View,
        private val api: SendMessageApi) : SendMessageContract.Presenter {

    private val subscription = CompositeDisposable()

    init {
    }

    override fun start() {

    }

    override fun stop() {
        subscription.clear()
    }

    override fun sendMessage(phoneNumber: String, message: String) {
        view.showProgress()

        subscription.add(api.postMessage(SendMessageRequest(phoneNumber, message))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { view.hideProgress() }
                .subscribe({
                    if (it.success) {
                        view.clearInput()
                    } else {
                        view.showError(it.message)
                    }
                }, {
                    view.showError(it.message)
                }))
    }

    override fun submitPin(pin: String): Boolean {
        val correct = BuildConfig.PIN == pin
        if (correct) {
            view.showAdminView()
        } else {
            view.showPinError()
        }
        return correct
    }

}