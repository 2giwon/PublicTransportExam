package com.egiwon.publictransport.ui.arrivalinfo

import androidx.annotation.LayoutRes
import com.egiwon.publictransport.base.BaseActivity
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource

class BusStationArrivalActivity(
    @LayoutRes private val layoutResId: Int
) : BaseActivity<BusStationArrivalPresenter>(layoutResId), BusStationArrivalContract.View {

    override val mainPresenter: BusStationArrivalPresenter by lazy {
        BusStationArrivalPresenter(
            this,
            BusServiceRepositoryImpl.getInstance(BusServiceRemoteDataSource.getInstance())
        )
    }

    override fun showToast(textResId: Int) {

    }

    override fun showToast(text: String) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

}