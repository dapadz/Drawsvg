package com.dapadz.drawsvg.core

import com.dapadz.drawsvg.core.form.SvgForm

open class Svg(
    open val width: Int,
    open val height: Int,
    open val forms: List<SvgForm>
) {
    fun scale(factor: Float): Svg {
        return Svg((width * factor).toInt(), (height* factor).toInt(), forms)
    }
}