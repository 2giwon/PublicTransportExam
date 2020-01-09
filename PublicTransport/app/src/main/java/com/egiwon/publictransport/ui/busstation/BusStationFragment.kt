package com.egiwon.publictransport.ui.busstation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.egiwon.publictransport.R
import com.egiwon.publictransport.data.StationCallback
import com.egiwon.publictransport.data.response.ServiceResult
import com.egiwon.publictransport.data.service.StationSearchService
import kotlinx.android.synthetic.main.fragment_busstation.*

class BusStationFragment : Fragment(R.layout.fragment_busstation), StationCallback {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_search.setOnClickListener {
            StationSearchService(
                this
            ).getStationInfo(et_search.text.toString(), serviceKey)
        }
    }

    override fun onSuccess(stationInfos: ServiceResult) {
        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            requireContext().getString(R.string.error_load_station)
                    + throwable.localizedMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val serviceKey =
            "zdPk2xTv930O2l4zxAjyh%2BCZeZKCY3UYhKsoFTlOfAvhCcEt0DdPZFxfQFsDrQgWLcTQHPunYimxI9WnTm3PFQ%3D%3D"
    }
}