package com.egiwon.publictransport.ui.busstation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.egiwon.publictransport.MainActivity
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.StationCallback
import com.egiwon.publictransport.data.response.Item
import com.egiwon.publictransport.data.service.StationSearchService
import kotlinx.android.synthetic.main.fragment_busstation.*

class BusStationFragment : Fragment(R.layout.fragment_busstation), StationCallback {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_search.setOnClickListener {
            showProgressBar()
            if (et_search.text.isNullOrBlank()) {
                errorSearchStationEmpty()
            } else {
                StationSearchService(
                    this
                ).getStationInfo(et_search.text.toString(), serviceKey)
            }
        }

        with(rv_station) {
            adapter = BusStationAdapter(onClick)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

    }

    private val onClick: (Item) -> Unit = { item ->
        (requireActivity() as MainActivity).run {
            setFavoriteSubject.onNext(item)
        }
    }

    override fun onSuccess(stationInfos: List<Item>) {
        hideProgressBar()
        (rv_station.adapter as? BusStationAdapter)?.setItems(stationInfos)
    }

    override fun onFailure(throwable: Throwable) {
        errorloadStationFail(throwable)
    }

    private fun showProgressBar() {
        progress_circular.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_circular.visibility = View.GONE
    }

    private fun errorloadStationFail(throwable: Throwable) {
        hideProgressBar()
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_load_station)
                    + throwable.localizedMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun errorSearchStationEmpty() {
        hideProgressBar()
        Toast.makeText(
            requireContext(),
            getString(R.string.error_empty_station_name),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val serviceKey =
            "zdPk2xTv930O2l4zxAjyh%2BCZeZKCY3UYhKsoFTlOfAvhCcEt0DdPZFxfQFsDrQgWLcTQHPunYimxI9WnTm3PFQ%3D%3D"
    }
}