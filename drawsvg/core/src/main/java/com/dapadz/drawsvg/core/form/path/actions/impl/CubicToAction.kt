package com.dapadz.drawsvg.core.form.path.actions.impl

import com.dapadz.drawsvg.core.form.path.actions.PathAction
import com.dapadz.drawsvg.core.geometry.Point


data class CubicToAction(
    override val point: Point,
    val point1: Point,
    val point2: Point
) : PathAction