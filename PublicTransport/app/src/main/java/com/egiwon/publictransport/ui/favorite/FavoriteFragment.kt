package com.egiwon.publictransport.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.egiwon.publictransport.MainActivity
import com.egiwon.publictransport.R
import com.egiwon.publictransport.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment
    : BaseFragment<FavoriteContract.Presenter>(R.layout.fragment_favorite), FavoriteContract.View {

    override val presenter: FavoriteContract.Presenter by lazy {
        FavoritePresenter(this)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rv_favorite_station) {
            adapter = FavoriteAdapter()
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            presenter.requestFavoriteStationList()
        }

    }

    override fun getFavoriteStationList() {
        (rv_favorite_station.adapter as? FavoriteAdapter)?.run {
            (requireActivity() as? MainActivity)?.requestFavoriteList {
                setItems(it)
            }
        }
    }

    override fun showLoading() = Unit
    override fun hideLoading() = Unit
}