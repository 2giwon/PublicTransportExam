package com.egiwon.publictransport.ui.favorite

import com.egiwon.publictransport.base.BasePresenter
import com.egiwon.publictransport.data.response.Item

class FavoritePresenter(
    private val view: FavoriteContract.View
) : FavoriteContract.Presenter, BasePresenter<Item>() {

    override fun requestFavoriteStationList() {
        view.getFavoriteStationList()
    }

}