package com.egiwon.publictransport.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<P : BaseContract.Presenter>(
    @LayoutRes layoutResId: Int
) : AppCompatActivity(layoutResId) {

    protected abstract val mainPresenter: P

    override fun onDestroy() {
        mainPresenter.clearDisposables()
        super.onDestroy()
    }
}