package com.egiwon.publictransport.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.egiwon.publictransport.MainActivity
import com.egiwon.publictransport.R
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rv_favorite_station) {
            adapter = FavoriteAdapter()
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            (adapter as? FavoriteAdapter)?.run {
                setItems((requireActivity() as MainActivity).favouriteSet.toList())
            }
        }

    }
}