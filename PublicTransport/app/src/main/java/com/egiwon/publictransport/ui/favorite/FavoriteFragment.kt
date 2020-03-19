package com.egiwon.publictransport.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseFragment
import com.egiwon.publictransport.data.BusServiceRepositoryImpl
import com.egiwon.publictransport.data.local.BusServiceLocalDataSourceImpl
import com.egiwon.publictransport.data.local.BusStationDatabase
import com.egiwon.publictransport.data.local.model.BusStation
import com.egiwon.publictransport.data.remote.BusServiceRemoteDataSourceImpl
import com.egiwon.publictransport.ui.arrivalinfo.BusStationArrivalActivity
import com.egiwon.publictransport.ui.busstation.BusStationFragment.Companion.KEY_ITEM_ARSID
import com.egiwon.publictransport.ui.busstation.BusStationFragment.Companion.KEY_ITEM_TITLE
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
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

        rv_favorite_station.initAdapter()
        presenter.requestFavoriteStationList()
    }

    private fun RecyclerView.initAdapter() {
        adapter = FavoriteAdapter(onClick, onMovedItemListener, onClickTagListener)
        setHasFixedSize(true)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        ItemTouchHelper(
            SwipeToDeleteCallback(
                onDelete,
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            )
        ).apply { attachToRecyclerView(this@initAdapter) }
    }

    override fun showFavoriteStationList(favoriteBusStations: List<BusStation>) {
        (rv_favorite_station.adapter as? FavoriteAdapter)?.run {
            setItems(favoriteBusStations)
        }
    }

    override fun errorFavoriteStationsLoadFail() {
        showToast(getString(R.string.error_favorite_load_fail))
    }

    override fun refreshFavoriteAdapterList() {
        Snackbar
            .make(
                rv_favorite_station,
                getString(R.string.notify_delete_favorite_bus_station),
                Snackbar.LENGTH_LONG
            )
            .setAction(getString(R.string.cancel)) { presenter.restoreDeletedFavoriteStation() }
            .setAnchorView(requireActivity().nav_view)
            .show()
    }

    private val onClick: onClickListener = {

        val intent = Intent(requireContext(), BusStationArrivalActivity::class.java).apply {
            putExtra(KEY_ITEM_ARSID, it.arsId)
            putExtra(KEY_ITEM_TITLE, it.stationName)
        }
        startActivity(intent)
    }

    private val onDelete: onRemoveItemListener = {
        (rv_favorite_station.adapter as? FavoriteAdapter)?.let { adapter ->
            val busStation = adapter.onGetItem(it)
            adapter.onRemoveItem(it)

            presenter.deleteFavoriteStation(busStation, it)
            adapter.notifyDataSetChanged()
        }
    }

    private val onMovedItemListener: onMovedItemListener = { list ->
        presenter.updateFavoriteStationListFromTo(list)
    }

    private val onClickTagListener: onClickTagListener = { id, tag ->
        presenter.setFavoriteStationTag(id, tag)
    }
}