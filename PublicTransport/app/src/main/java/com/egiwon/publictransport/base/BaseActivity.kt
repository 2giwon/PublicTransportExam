package com.egiwon.publictransport.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(
    @LayoutRes layoutResId: Int
) : AppCompatActivity(layoutResId)