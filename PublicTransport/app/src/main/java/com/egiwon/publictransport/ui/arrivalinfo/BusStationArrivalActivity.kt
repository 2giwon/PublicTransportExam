package com.egiwon.publictransport.ui.arrivalinfo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseActivity
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.ui.busstation.BusStationFragment.Companion.KEY_ITEM
import com.egiwon.publictransport.ui.busstation.BusStationFragment.Companion.KEY_RESULT_FAVOURITE
import kotlinx.android.synthetic.main.activity_bus_arrival_info.*

class BusStationArrivalActivity(
    @LayoutRes private val layoutResId: Int = R.layout.activity_bus_arrival_info
) : BaseActivity<BusStationArrivalPresenter>(layoutResId), BusStationArrivalContract.View {

    private val returnIntent: Intent = Intent()

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

        intent.extras?.getString(KEY_ITEM)?.let { arsId ->
            mainPresenter.getBusStationArrivalInfo(arsId)

            fb_favourite_bus.setOnClickListener {
                addFavouriteBusStation(arsId)
            }
        }

    }

    override fun showBusStationArrivalInfo(arrivalItems: List<ArrivalInfoItem>) {
        (rv_arrival_bus.adapter as? BusStationArrivalAdapter)?.setItems(arrivalItems)
    }

    override fun showLoadFail(throwable: Throwable) {
        showToast(getString(R.string.error_load_fail))
    }

    override fun showResultAddFavouriteBusStation(station: ArrivalInfoItem) {
        returnIntent.putExtra(KEY_RESULT_FAVOURITE, station.arsId)
        showToast(getString(R.string.add_favourite_bus_station, station.stNm))
        setResult(Activity.RESULT_OK, returnIntent)
    }


    private fun addFavouriteBusStation(arsId: String) {
        mainPresenter.addFavouriteBusStation(arsId)
    }

}