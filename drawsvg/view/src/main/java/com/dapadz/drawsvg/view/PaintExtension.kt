package com.dapadz.drawsvg.view

import android.graphics.Paint
import com.dapadz.drawsvg.core.form.SvgForm
import androidx.core.graphics.toColorInt

internal val SvgForm.strokePaint get() = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.STROKE
    this@strokePaint.strokeColor?.let { color = it.toColorInt() }
    this@strokePaint.strokeWidth?.let { strokeWidth = it }
}.takeIf { strokeColor != null && strokeWidth != null }

internal val SvgForm.fillPaint get() = if (fillColor != null) {
    Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        this@fillPaint.strokeColor?.let { color = it.toColorInt() }
    }
} else null