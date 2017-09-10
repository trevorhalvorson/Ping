package com.trevorhalvorson.ping

interface BaseView<in T : BasePresenter> {

    fun setPresenter(presenter: T)

}