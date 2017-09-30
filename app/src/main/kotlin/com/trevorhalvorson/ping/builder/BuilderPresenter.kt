package com.trevorhalvorson.ping.builder

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BuilderPresenter @Inject constructor(
        private val view: BuilderContract.View,
        private val api: BuilderApi) : BuilderContract.Presenter {

    private val TAG = "BuilderPresenter";
    private val subscription = CompositeDisposable()

    override fun start() {

    }

    override fun stop() {
        subscription.clear()
    }

    override fun startBuilder(config: BuilderConfig) {
        view.showProgress()

        subscription.add(api.startBuilder(config)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { view.hideProgress() }
                .subscribe({
                    Log.d(TAG, it.toString())
                }, {
                    Log.e(TAG, "Error starting builder", it)
                }))
    }

}