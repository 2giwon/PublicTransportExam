package com.egiwon.publictransport.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<P : BaseContract.Presenter>(
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId), BaseContract.View {

    abstract val presenter: P

    override fun onDestroyView() {
        presenter.clearDisposables()
        super.onDestroyView()
    }

    override fun showToast(textResId: Int) = showToast(getString(textResId))

    override fun showToast(text: String) =
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()

    override fun showLoading() = Unit
    override fun hideLoading() = Unit
}