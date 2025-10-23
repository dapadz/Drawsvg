package com.dapadz.drawsvg.core.form.path.actions.parser.factory

import com.dapadz.drawsvg.core.form.path.actions.impl.LineToAction
import com.dapadz.drawsvg.core.form.path.actions.PathAction
import com.dapadz.drawsvg.core.geometry.Point
import com.dapadz.drawsvg.core.utils.toFloatOrZero

class VerticalLineActionFactory(
    private val previousAction: PathAction?
): PathActionFactory {
    override fun create(values: List<String>): PathAction {
        val x = previousAction?.point?.x ?: run {
            throw NullPointerException("Previous action is null")
        }
        val y = values.first().toFloatOrZero()
        return LineToAction(Point(x, y))
    }
}