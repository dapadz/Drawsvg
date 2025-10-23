package com.dapadz.drawsvg.core.form.path.actions.parser.factory

import com.dapadz.drawsvg.core.form.path.actions.impl.CubicToAction
import com.dapadz.drawsvg.core.form.path.actions.PathAction
import com.dapadz.drawsvg.core.geometry.Point
import com.dapadz.drawsvg.core.utils.toFloatOrZero


class CubicToActionFactory: PathActionFactory {
    override fun create(values: List<String>): PathAction {
        val x1 = values[0].toFloatOrZero()
        val y1 = values[1].toFloatOrZero()
        val x2 = values[2].toFloatOrZero()
        val y2 = values[3].toFloatOrZero()
        val x = values[4].toFloatOrZero()
        val y = values[5].toFloatOrZero()
        val point = Point(x, y)
        val point1 = Point(x1, y1)
        val point2 = Point(x2, y2)
        return CubicToAction(point, point1, point2)
    }
}