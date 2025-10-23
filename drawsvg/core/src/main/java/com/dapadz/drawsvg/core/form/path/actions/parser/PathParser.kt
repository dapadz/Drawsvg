package com.dapadz.drawsvg.core.form.path.actions.parser

import com.dapadz.drawsvg.core.form.path.actions.PathAction
import com.dapadz.drawsvg.core.form.path.actions.parser.factory.CloseActionFactory
import com.dapadz.drawsvg.core.form.path.actions.parser.factory.CubicToActionFactory
import com.dapadz.drawsvg.core.form.path.actions.parser.factory.HorizontalLineActionFactory
import com.dapadz.drawsvg.core.form.path.actions.parser.factory.LineToActionFactory
import com.dapadz.drawsvg.core.form.path.actions.parser.factory.MoveToActionFactory
import com.dapadz.drawsvg.core.form.path.actions.parser.factory.QuadToActionFactory
import com.dapadz.drawsvg.core.form.path.actions.parser.factory.VerticalLineActionFactory
import java.util.LinkedList

class PathParser(
    private val path: String
) {

    private val actionChars =
        listOf('M', 'm', 'L', 'l', 'H', 'h', 'V', 'v', 'C', 'c', 'Q', 'q', 'S', 's', 'Z', 'z')

    fun parse(): LinkedList<PathAction> {
        val actions = LinkedList<PathAction>()
        var i = 0
        while (i < path.length) {
            val char = path[i]
            if (char.isNotAction) {
                i++
                continue
            }
            val valueStringBuilder = StringBuilder()
            var j = i + 1
            while (j < path.length) {
                val valueChar = path[j]
                if (valueChar.isAction) {
                    break
                }
                valueStringBuilder.append(valueChar)
                j++
            }
            val value = valueStringBuilder.toString()
            val previousAction = actions.lastOrNull()
            val action = createAction(char, value, previousAction)
            actions.addLast(action)
            i = j
        }
        return actions
    }

    private fun createAction(
        actionChar: Char,
        value: String,
        previousAction: PathAction?
    ): PathAction {
        val values = value.split("[\\s,]".toRegex())
        return when (actionChar) {
            'M', 'm' -> MoveToActionFactory()
            'L', 'l' -> LineToActionFactory()
            'H', 'h' -> HorizontalLineActionFactory(previousAction)
            'V', 'v' -> VerticalLineActionFactory(previousAction)
            'C', 'c' -> CubicToActionFactory()
            'Q', 'q' -> QuadToActionFactory()
            'Z', 'z' -> CloseActionFactory(previousAction)
            else -> throw Exception("Unknown SVG action \"$actionChar $value\"")
        }.create(values)
    }

    private val Char.isAction: Boolean
        get() = actionChars.contains(this)

    private val Char.isNotAction: Boolean
        get() = !isAction
}