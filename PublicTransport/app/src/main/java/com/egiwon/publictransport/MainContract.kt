package com.egiwon.publictransport

import com.egiwon.publictransport.base.BaseContract
import com.egiwon.publictransport.data.response.Item
import io.reactivex.subjects.BehaviorSubject

interface MainContract : BaseContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter {
        fun requestFavoriteSubject(block: (BehaviorSubject<Item>) -> Unit)
        fun requestFavoriteList(block: (List<Item>) -> Unit)
    }
}