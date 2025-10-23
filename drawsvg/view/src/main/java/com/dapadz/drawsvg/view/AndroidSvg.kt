package com.dapadz.drawsvg.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import androidx.annotation.MainThread
import androidx.core.graphics.withSave
import com.dapadz.drawsvg.core.Svg
import com.dapadz.drawsvg.core.form.PathForm
import com.dapadz.drawsvg.core.form.SvgForm
import com.dapadz.drawsvg.core.form.path.actions.impl.CloseAction
import com.dapadz.drawsvg.core.form.path.actions.impl.CubicToAction
import com.dapadz.drawsvg.core.form.path.actions.impl.LineToAction
import com.dapadz.drawsvg.core.form.path.actions.impl.MoveToAction
import com.dapadz.drawsvg.core.form.path.actions.impl.QuadToAction
import java.util.UUID

class AndroidSvg internal constructor(
    override val width: Int,
    override val height: Int,
    override val forms: List<SvgForm>
): Svg(width, height, forms) {

    companion object {
        const val TAG = "AndroidSvg"
    }

    internal val formsById = forms.associateBy { UUID.randomUUID() }
    internal val pathById = formsById.filter { it.value is PathForm }.mapValues { Path() }

    @MainThread
    fun draw(
        canvas: Canvas,
        overridePaint: Paint? = null
    ) {
        canvas.withSave {
            formsById.forEach { id, form ->
                when (form) {
                    is PathForm -> drawSvgPath(pathById[id], form, overridePaint)
                }
            }
        }
    }

    private fun Canvas.drawSvgPath(
        path: Path?,
        form: PathForm,
        overridePaint: Paint?
    ) {
        if (path == null) {
            Log.w(TAG, "try draw nullable path from $form")
            return
        }
        path.reset()
        form.actions.forEach { action ->
            when (action) {
                is MoveToAction -> path.moveTo(action.point.x.dpf, action.point.y.dpf)
                is LineToAction -> path.lineTo(action.point.x.dpf, action.point.y.dpf)
                is QuadToAction -> path.quadTo(action.point1.x.dpf, action.point1.y.dpf, action.point.x.dpf, action.point.x.dpf)
                is CubicToAction -> path.cubicTo(action.point1.x.dpf, action.point1.y.dpf, action.point2.x.dpf, action.point2.y.dpf, action.point.x.dpf, action.point.y.dpf)
                is CloseAction -> path.close()
            }
        }
        form.strokePaint?.let { drawPath(path, overridePaint ?: it) }
        form.fillPaint?.let { drawPath(path, overridePaint ?: it) }
    }

}