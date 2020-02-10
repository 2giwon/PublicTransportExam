package com.egiwon.publictransport.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseFragment
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.local.BusServiceLocalDataSourceImpl
import com.egiwon.publictransport.data.local.BusStationDatabase
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSourceImpl
import com.egiwon.publictransport.ui.arrivalinfo.BusStationArrivalActivity
import com.egiwon.publictransport.ui.busstation.BusStationFragment.Companion.KEY_ITEM
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment
    : BaseFragment<FavoriteContract.Presenter>(R.layout.fragment_favorite), FavoriteContract.View {

    override val presenter: FavoriteContract.Presenter by lazy {
        FavoritePresenter(
            this,
            BusServiceRepositoryImpl.getInstance(
                BusServiceRemoteDataSourceImpl.getInstance(),
                BusServiceLocalDataSourceImpl.getInstance(
                    BusStationDatabase.getInstance(requireContext()).busStationDao()
                )
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rv_favorite_station) {
            adapter = FavoriteAdapter(onClick)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            presenter.requestFavoriteStationList()
        }

    }

    override fun showFavoriteStationList(favoriteBusStations: List<BusStation>) {
        (rv_favorite_station.adapter as? FavoriteAdapter)?.run {
            setItems(favoriteBusStations)
        }
    }

    override fun errorFavoriteStationsLoadFail() {
        showToast(getString(R.string.error_favorite_load_fail))
    }

    private val onClick: (BusStation) -> Unit = {

        val intent = Intent(requireContext(), BusStationArrivalActivity::class.java).apply {
            putExtra(KEY_ITEM, it.arsId)
        }
        startActivity(intent)
    }

    override fun showLoading() = Unit
    override fun hideLoading() = Unit
}