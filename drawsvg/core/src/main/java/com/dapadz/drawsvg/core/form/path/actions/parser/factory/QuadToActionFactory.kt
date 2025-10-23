package com.dapadz.drawsvg.core.form.path.actions.parser.factory

import com.dapadz.drawsvg.core.form.path.actions.PathAction
import com.dapadz.drawsvg.core.form.path.actions.impl.QuadToAction
import com.dapadz.drawsvg.core.geometry.Point
import com.dapadz.drawsvg.core.utils.toFloatOrZero

class QuadToActionFactory: PathActionFactory {
    override fun create(values: List<String>): PathAction {
        val x1 = values[0].toFloatOrZero()
        val y1 = values[1].toFloatOrZero()
        val x = values[2].toFloatOrZero()
        val y = values[3].toFloatOrZero()
        val point = Point(x, y)
        val point1 = Point(x1, y1)
        return QuadToAction(point, point1)
    }
}