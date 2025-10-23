package com.dapadz.drawsvg.core.form.path.actions.parser.factory

import com.dapadz.drawsvg.core.form.path.actions.PathAction

interface PathActionFactory {
    fun create(values: List<String>): PathAction
}