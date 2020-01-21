package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BasePresenter

class FavoritePresenter(
    private val view: FavoriteContract.View
) : FavoriteContract.Presenter, BasePresenter() {

    override fun requestFavoriteStationList() {
        view.getFavoriteStationList()
    }

}