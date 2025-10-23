package com.dapadz.drawsvg.core.form


sealed interface SvgForm {
    val fillColor: String?
    val fillAlpha: Float?
    val strokeColor: String?
    val strokeWidth: Float?
    val strokeAlpha: Float?
}