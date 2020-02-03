package com.egiwon.publictransport.ui.arrivalinfo

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseActivity
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import kotlinx.android.synthetic.main.activity_bus_arrival_info.*

class BusStationArrivalActivity(
    @LayoutRes private val layoutResId: Int = R.layout.activity_bus_arrival_info
) : BaseActivity<BusStationArrivalPresenter>(layoutResId), BusStationArrivalContract.View {

    override val mainPresenter: BusStationArrivalPresenter by lazy {
        BusStationArrivalPresenter(
            this,
            BusServiceRepositoryImpl.getInstance(BusServiceRemoteDataSource.getInstance())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rv_arrival_bus.adapter = BusStationArrivalAdapter()
        rv_arrival_bus.setHasFixedSize(true)

        intent.extras?.getString("keyitem")?.let {
            mainPresenter.getBusStationArrivalInfo(it)
        }
    }

    override fun showBusStationArrivalInfo(arrivalItems: List<ArrivalInfoItem>) {
        (rv_arrival_bus.adapter as? BusStationArrivalAdapter)?.setItems(arrivalItems)
    }

    override fun showLoadFail(throwable: Throwable) {
        showToast(getString(R.string.error_load_fail))
    }

}