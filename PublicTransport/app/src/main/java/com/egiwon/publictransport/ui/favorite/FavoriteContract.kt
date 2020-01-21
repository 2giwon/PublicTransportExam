package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BaseContract

interface FavoriteContract : BaseContract {

    interface View : BaseContract.View {
        fun getFavoriteStationList()
    }

    interface Presenter : BaseContract.Presenter {
        fun requestFavoriteStationList()
    }
}