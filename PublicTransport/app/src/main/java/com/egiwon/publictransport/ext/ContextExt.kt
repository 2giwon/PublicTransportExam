package com.egiwon.publictransport.ext

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Context.hideKeyboard() {
    GlobalScope.launch {
        delay(100)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            0
        )
    }
}
