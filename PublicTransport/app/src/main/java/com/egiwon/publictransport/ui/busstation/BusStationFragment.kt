package com.egiwon.publictransport.ui.busstation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.egiwon.publictransport.MainActivity
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseFragment
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.local.BusServiceLocalDataSourceImpl
import com.egiwon.publictransport.data.local.BusStationDatabase
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSourceImpl
import com.egiwon.publictransport.data.response.Item
import com.egiwon.publictransport.ext.hideKeyboard
import com.egiwon.publictransport.ui.arrivalinfo.BusStationArrivalActivity
import kotlinx.android.synthetic.main.fragment_busstation.*

class BusStationFragment : BaseFragment<BusStationContract.Presenter>(R.layout.fragment_busstation),
    BusStationContract.View {

    override val presenter: BusStationPresenter by lazy {
        BusStationPresenter(
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

        iv_search.setOnClickListener {
            presenter.requestBusStations(et_search.text.toString())
        }

        with(rv_station) {
            adapter = BusStationAdapter(onClick)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        presenter.requestBusStations()
        initSearch()
    }

    private fun initSearch() {
        et_search.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                presenter.requestBusStations(et_search.text.toString())
                requireContext().hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    override fun showSearchBusStationResult(resultList: List<Item>) {
        hideEmptyBus()
        (rv_station.adapter as? BusStationAdapter)?.setItems(resultList)
    }

    override fun showSearchBusCache(resultList: List<Item>, searchQuery: String) {
        showSearchBusStationResult(resultList)
        et_search.setText(searchQuery)
    }

    override fun showErrorSearchNameEmpty() =
        showToast(R.string.error_empty_station_name)

    override fun showErrorLoadBusStationFail() =
        showToast(R.string.error_load_fail)

    override fun showErrorResultEmpty() =
        showToast(R.string.empty_bus)

    override fun sendFavoriteBusStation(station: Item) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_FAVORITE_ITEM) {
            if (resultCode == Activity.RESULT_OK) {
                data?.getStringExtra(KEY_RESULT_FAVORITE)?.let {
                    findStationByArsId(it)
                }

            }
        }
    }

    private fun findStationByArsId(arsId: String) {
        presenter.requestFindBusStationByArsId(arsId)
    }

    private val onClick: (Item) -> Unit = { item ->

        val intent = Intent(requireContext(), BusStationArrivalActivity::class.java).apply {
            putExtra(KEY_ITEM, item.arsId)
        }
        startActivityForResult(intent, REQUEST_FAVORITE_ITEM)
    }

    private fun hideEmptyBus() {
        iv_empty_bus.visibility = View.GONE
        tv_empty_bus.visibility = View.GONE
    }

    companion object {
        const val KEY_ITEM = "keyitem"
        const val KEY_RESULT_FAVORITE = "result_favorite"

        const val REQUEST_FAVORITE_ITEM = 1
    }

}