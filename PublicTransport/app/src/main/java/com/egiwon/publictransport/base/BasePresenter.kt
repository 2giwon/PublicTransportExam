package com.egiwon.publictransport.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T : Any> : BaseContract.Presenter {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun clearDisposables() {
        compositeDisposable.clear()
    }

    protected fun MutableList<T>.setItems(items: List<T>, block: (List<T>) -> Unit) {
        clear()
        addAll(items)
        block(items)
    }
}