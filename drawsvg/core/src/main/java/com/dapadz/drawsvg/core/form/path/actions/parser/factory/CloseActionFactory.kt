package com.dapadz.drawsvg.core.form.path.actions.parser.factory

import com.dapadz.drawsvg.core.form.path.actions.impl.CloseAction
import com.dapadz.drawsvg.core.form.path.actions.PathAction

class CloseActionFactory(
    private val previousAction: PathAction?
): PathActionFactory {
    override fun create(values: List<String>): PathAction {
        previousAction?.point?.y ?: run {
            throw NullPointerException("Previous action is null")
        }
        return CloseAction(previousAction.point)
    }
}