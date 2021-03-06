package com.egiwon.publictransport

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.egiwon.publictransport.base.BaseActivity
import com.egiwon.publictransport.data.local.model.BusStations
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    BaseActivity<MainContract.Presenter>(R.layout.activity_main), MainContract.View {

    override val mainPresenter: MainContract.Presenter by lazy {
        MainPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_bus_station, R.id.navigation_favorite
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    fun getBusStation(block: () -> BusStations) =
        mainPresenter.getSearchBusStationResult(block)

    fun requestRecentlySearchBusStation(block: (BusStations) -> Unit) =
        mainPresenter.requestBusStationCache(block)
}
