package com.egiwon.publictransport.base

interface BaseContract {

    interface View {
        fun showToast(textResId: Int)

        fun showToast(text: String)

        fun showLoading()

        fun hideLoading()

    }

    interface Presenter {
        fun clearDisposables()
    }
}