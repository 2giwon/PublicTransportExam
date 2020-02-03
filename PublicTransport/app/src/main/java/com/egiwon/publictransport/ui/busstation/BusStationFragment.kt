package com.egiwon.publictransport.ui.busstation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.egiwon.publictransport.MainActivity
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseFragment
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSource
import com.egiwon.publictransport.data.response.Item
import com.egiwon.publictransport.ui.arrivalinfo.BusStationArrivalActivity
import kotlinx.android.synthetic.main.fragment_busstation.*

class BusStationFragment : BaseFragment<BusStationContract.Presenter>(R.layout.fragment_busstation),
    BusStationContract.View {

    override val presenter: BusStationPresenter by lazy {
        BusStationPresenter(
            this,
            BusServiceRepositoryImpl.getInstance(BusServiceRemoteDataSource.getInstance())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_search.setOnClickListener {
            presenter.requestBusStations(et_search.text.toString())
        }

        with(rv_station) {
            adapter = BusStationAdapter(onClick)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

    }

    override fun showSearchBusStationResult(resultList: List<Item>) {
        hideEmptyBus()
        (rv_station.adapter as? BusStationAdapter)?.setItems(resultList)
    }

    override fun showErrorSearchNameEmpty() =
        showToast(R.string.error_empty_station_name)

    override fun showErrorLoadBusStationFail() =
        showToast(R.string.error_load_fail)

    override fun showErrorResultEmpty() =
        showToast(R.string.empty_bus)

    override fun sendFavouriteBusStation(station: Item) {
        (requireActivity() as? MainActivity)?.requestFavoriteItemToSend {
            it.onNext(station)
        }
    }

    override fun showLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_circular.visibility = View.GONE
    }

    private val onClick: (Item) -> Unit = { item ->
        //        presenter.requestFavouriteBusStationToSend(item)
        val intent = Intent(requireContext(), BusStationArrivalActivity::class.java).apply {
            putExtra("keyitem", item.arsId)
        }
        startActivity(intent)
    }

    private fun hideEmptyBus() {
        iv_empty_bus.visibility = View.GONE
        tv_empty_bus.visibility = View.GONE
    }

}