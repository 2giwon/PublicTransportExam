package com.egiwon.publictransport

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.response.Item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class MainPresenter(
    private val view: MainContract.View
) : BasePresenter(), MainContract.Presenter {

    private val favouriteSet = mutableSetOf<Item>()

    private val _favoriteSubject: BehaviorSubject<Item> = BehaviorSubject.create()
    private val favoriteSubject: BehaviorSubject<Item> get() = _favoriteSubject

    init {
        _favoriteSubject.subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                favouriteSet.add(it)
            }.addDisposable()
    }

    override fun requestFavoriteSubject(block: (BehaviorSubject<Item>) -> Unit) {
        block(favoriteSubject)
    }

    override fun requestFavoriteList(block: (List<Item>) -> Unit) {
        block(favouriteSet.toList())
    }


}