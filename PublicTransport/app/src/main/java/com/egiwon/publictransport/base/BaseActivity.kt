package com.egiwon.publictransport.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<P : BaseContract.Presenter>(
    @LayoutRes layoutResId: Int
) : AppCompatActivity(layoutResId), BaseContract.View {

    protected abstract val mainPresenter: P

    override fun onDestroy() {
        mainPresenter.clearDisposables()
        super.onDestroy()
    }

    override fun showToast(textResId: Int) = showToast(getString(textResId))

    override fun showToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()


    override fun showLoading() = Unit
    override fun hideLoading() = Unit
}