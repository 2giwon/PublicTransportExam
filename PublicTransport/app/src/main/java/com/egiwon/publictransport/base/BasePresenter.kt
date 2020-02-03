package com.egiwon.publictransport.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter : BaseContract.Presenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun Disposable.addDisposable() = compositeDisposable.add(this)

    override fun clearDisposables() {
        compositeDisposable.clear()
    }
}