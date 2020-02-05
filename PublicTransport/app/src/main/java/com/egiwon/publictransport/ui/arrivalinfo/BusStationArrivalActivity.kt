package com.egiwon.publictransport.ui.arrivalinfo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseActivity
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.local.BusServiceLocalDataSourceImpl
import com.egiwon.publictransport.data.local.FavoriteBusStationDatabase
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSourceImpl
import com.egiwon.publictransport.data.response.ArrivalInfoItem
import com.egiwon.publictransport.ui.busstation.BusStationFragment.Companion.KEY_ITEM
import com.egiwon.publictransport.ui.busstation.BusStationFragment.Companion.KEY_RESULT_FAVORITE
import kotlinx.android.synthetic.main.activity_bus_arrival_info.*

class BusStationArrivalActivity(
    @LayoutRes private val layoutResId: Int = R.layout.activity_bus_arrival_info
) : BaseActivity<BusStationArrivalPresenter>(layoutResId), BusStationArrivalContract.View {

    private val favoriteDeliveryIntent: Intent = Intent()

    override val mainPresenter: BusStationArrivalPresenter by lazy {
        BusStationArrivalPresenter(
            this,
            BusServiceRepositoryImpl.getInstance(
                BusServiceRemoteDataSourceImpl.getInstance(),
                BusServiceLocalDataSourceImpl.getInstance(
                    FavoriteBusStationDatabase.getInstance(applicationContext).favoriteBusStationDao()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rv_arrival_bus.adapter = BusStationArrivalAdapter()
        rv_arrival_bus.setHasFixedSize(true)

        intent.extras?.getString(KEY_ITEM)?.let { arsId ->
            mainPresenter.getBusStationArrivalInfo(arsId)
            mainPresenter.findFavoriteBusStationByArsId(arsId)

            fb_favorite_bus.setOnClickListener {
                addFavoriteBusStation(arsId)
            }

            swipe_container.setOnRefreshListener {
                mainPresenter.getBusStationArrivalInfo(arsId)
            }
        }

    }

    override fun showBusStationArrivalInfo(arrivalItems: List<ArrivalInfoItem>) {
        (rv_arrival_bus.adapter as? BusStationArrivalAdapter)?.setItems(arrivalItems)
    }

    override fun showLoadFail(throwable: Throwable) = showToast(getString(R.string.error_load_fail))

    override fun showResultAddFavoriteBusStation(station: ArrivalInfoItem) {
        favoriteDeliveryIntent.putExtra(KEY_RESULT_FAVORITE, station.arsId)
        showToast(getString(R.string.add_favorite_bus_station, station.stNm))
        setResult(Activity.RESULT_OK, favoriteDeliveryIntent)
    }

    override fun showFavoriteButton() {
        fb_favorite_bus.visibility = View.VISIBLE
    }

    override fun hideFavoriteButton() {
        fb_favorite_bus.visibility = View.GONE
    }

    override fun showLoading() {
        swipe_container.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_container.isRefreshing = false
    }

    private fun addFavoriteBusStation(arsId: String) = mainPresenter.addFavoriteBusStation(arsId)
}