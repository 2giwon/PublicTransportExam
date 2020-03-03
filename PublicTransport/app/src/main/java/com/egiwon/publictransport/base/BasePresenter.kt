package com.egiwon.publictransport.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : Any> : BaseContract.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun Disposable.addDisposable() = compositeDisposable.add(this)

    override fun clearDisposables() {
        compositeDisposable.clear()
    }

    protected fun MutableList<T>.setItems(items: List<T>, block: (List<T>) -> Unit) {
        clear()
        addAll(items)
        block(items)
    }
}