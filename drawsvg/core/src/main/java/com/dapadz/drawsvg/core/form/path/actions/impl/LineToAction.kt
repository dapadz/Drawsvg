package com.dapadz.drawsvg.core.form.path.actions.impl

import com.dapadz.drawsvg.core.form.path.actions.PathAction
import com.dapadz.drawsvg.core.geometry.Point


data class LineToAction(override val point: Point) : PathAction