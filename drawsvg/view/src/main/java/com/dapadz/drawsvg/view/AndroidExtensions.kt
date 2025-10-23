package com.dapadz.drawsvg.view

import android.content.res.Resources

internal val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

internal val Int.dpf: Float
    get() = this * Resources.getSystem().displayMetrics.density

internal val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

internal val Float.dpf: Float
    get() = this * Resources.getSystem().displayMetrics.density



