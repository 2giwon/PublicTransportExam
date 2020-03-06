package com.egiwon.publictransport.ui.busstation

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
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.local.model.BusStations
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSourceImpl
import com.egiwon.publictransport.ext.hideKeyboard
import com.egiwon.publictransport.ui.arrivalinfo.BusStationArrivalActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_busstation.*
import java.util.concurrent.TimeUnit

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

    private val compositeDisposable = CompositeDisposable()

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

        initSearch()
        bindRx()
        getBusStationCache()
    }

    private fun bindRx() {
        val textChanges = et_search.textChanges()
        val buttonClick = iv_search.clicks()
            .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .flatMapSingle { Single.fromCallable(et_search::getText) }

        Observable.merge(textChanges, buttonClick)
            .debounce(700, TimeUnit.MILLISECONDS)
            .filter(CharSequence::isNotBlank)
            .map(CharSequence::trim)
            .map(CharSequence::toString)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { presenter.requestBusStations(it) }
            .addTo(compositeDisposable)
    }

    private fun initSearch() {
        et_search.setOnKeyListener { _, keyCode, event ->
            when (event.action) {
                KeyEvent.ACTION_UP -> {
                    if (
                        keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER ||
                        keyCode == KeyEvent.KEYCODE_ENTER
                    ) {
                        presenter.requestBusStations(et_search.text.toString())
                        requireContext().hideKeyboard()
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }

    private fun getBusStationCache() {
        (requireActivity() as? MainActivity)?.requestRecentlySearchBusStation {
            showSearchBusCache(it)
        }
    }

    override fun sendSearchBusStationResult(busStations: BusStations) {
        (requireActivity() as? MainActivity)?.getBusStation { busStations }
    }

    override fun showSearchBusStationResult(busStations: BusStations) {
        hideEmptyBus()
        (rv_station.adapter as? BusStationAdapter)?.setItems(busStations.busStations)
        sendSearchBusStationResult(busStations)
    }

    override fun showSearchBusCache(busStations: BusStations) {
        showSearchBusStationResult(busStations)
        et_search.setText(busStations.searchQuery)
    }

    override fun showErrorSearchNameEmpty() =
        showToast(R.string.error_empty_station_name)

    override fun showErrorLoadBusStationFail() =
        showToast(R.string.error_load_fail)

    override fun showErrorResultEmpty() =
        showToast(R.string.empty_bus)

    override fun showLoading() {
        progress_circular.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_circular.visibility = View.GONE
    }

    private val onClick: (BusStation) -> Unit = { item ->

        val intent = Intent(requireContext(), BusStationArrivalActivity::class.java).apply {
            putExtra(KEY_ITEM_ARSID, item.arsId)
            putExtra(KEY_ITEM_TITLE, item.stationName)
        }
        startActivityForResult(intent, REQUEST_FAVORITE_ITEM)
    }

    private fun hideEmptyBus() {
        iv_empty_bus.visibility = View.GONE
        tv_empty_bus.visibility = View.GONE
    }

    companion object {
        const val KEY_ITEM_ARSID = "keyitem"
        const val KEY_ITEM_TITLE = "key_title"

        const val REQUEST_FAVORITE_ITEM = 1
    }

}