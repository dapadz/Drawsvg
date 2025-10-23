package com.dapadz.drawsvg.core.form

import com.dapadz.drawsvg.core.form.path.actions.PathAction
import com.dapadz.drawsvg.core.form.path.actions.parser.PathParser
import com.dapadz.drawsvg.core.geometry.Point
import java.util.LinkedList

class PathForm(
    path: String,
    override val fillColor: String?,
    override val strokeColor: String?,
    override val strokeWidth: Float?,
    override val fillAlpha: Float?,
    override val strokeAlpha: Float?,
) : SvgForm {
    val actions: LinkedList<PathAction> = PathParser(path).parse()
    val points: List<Point> = actions.map { it.point }
}