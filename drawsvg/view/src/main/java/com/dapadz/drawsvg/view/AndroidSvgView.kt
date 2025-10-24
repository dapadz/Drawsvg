package com.dapadz.drawsvg.view

import android.R.attr.strokeWidth
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.graphics.withSave
import com.dapadz.drawsvg.core.form.PathForm
import com.dapadz.drawsvg.view.parser.AndroidSvgParser
import java.io.InputStream

class AndroidSvgView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
): View(context, attributeSet) {

    private val parser = AndroidSvgParser()

    private var svgScale: Float = 1f
    private var touchPoint = PointF()

    private var svg: AndroidSvg? = null
        set(value) {
            field = value
            requestLayout()
        }

    private var tint: Paint? = null
        set(value) {
            field = value
            invalidate()
        }

    init {
        if (isInEditMode) {
            svg = AndroidSvgParser().fromDrawableRes(context, R.drawable.ic_straighten)
        }
    }

    fun setSvgDrawable(@DrawableRes drawableRes: Int) {
        svg = parser.fromDrawableRes(context, drawableRes)
    }

    fun setSvgFile(@RawRes drawableRaw: Int) {
        svg = parser.fromRawRes(context, drawableRaw)
    }

    fun setSvgFile(stream: InputStream) {
        svg = parser.fromSvgFile(stream)
    }

    fun setTint(@ColorRes colorRes: Int) {
        tint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = context.getColor(colorRes)
        }
    }

    fun setTintShader(tintShader: Shader) {
        tint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = tintShader
        }
    }

    fun setTintGradientWithHighlight(
        @ColorInt topColor: Int,
        @ColorInt bottomColor: Int
    ) {
        val s = svg ?: return

        val svgW = s.width.dp.toFloat()
        val svgH = s.height.dp.toFloat()
        if (svgW <= 0f || svgH <= 0f) return

        val linear = LinearGradient(
            /* x0 */ 0f, /* y0 */ 0f,
            /* x1 */ 0f, /* y1 */ svgH,
            intArrayOf(topColor, bottomColor),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )

        setTintShader(linear)
    }

    private fun isPointOnTooth(point: PointF): Boolean {
        if (svg == null) return false
        val pathForms = svg!!.forms.filterIsInstance<PathForm>()
        for (form in pathForms) {
            val maxPointLeftPoint = form.points.minOf { it.x }.dpf + strokeWidth
            val maxPointRightPoint = form.points.maxOf { it.x }.dpf + strokeWidth
            val maxPointTopPoint = form.points.minOf { it.y }.dpf + strokeWidth
            val maxPointBottomPoint = form.points.maxOf { it.y }.dpf + strokeWidth
            val isXContains = point.x in maxPointLeftPoint..maxPointRightPoint
            val isYContains = point.y in maxPointTopPoint..maxPointBottomPoint
            if (isYContains && isXContains) {
                return true
            }
        }
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!hasOnClickListeners()) return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchPoint = PointF(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                val deltaPoint = PointF(touchPoint.x, touchPoint.y)
                if (isPointOnTooth(deltaPoint)) {
                    performClick()
                }
                touchPoint = PointF()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.withSave {
            translate(
                paddingStart.toFloat(),
                paddingTop.toFloat()
            )
            scale(
                svgScale,
                svgScale
            )
            svg?.draw(
                this,
                tint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = svg?.width?.dp ?: 0
        val desiredHeight = svg?.height?.dp ?: 0

        val width = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            MeasureSpec.AT_MOST -> desiredWidth.coerceAtMost(MeasureSpec.getSize(widthMeasureSpec))
            else -> desiredWidth
        }

        val height = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
            MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
            else -> desiredHeight
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val s = svg ?: return

        val availW = (w - paddingLeft - paddingRight).toFloat().coerceAtLeast(0f)
        val availH = (h - paddingTop - paddingBottom).toFloat().coerceAtLeast(0f)

        val svgW = s.width.dp
        val svgH = s.width.dp

        val scaleX = if (svgW > 0f) availW / svgW else 1f
        val scaleY = if (svgH > 0f) availH / svgH else 1f
        svgScale = minOf(scaleX, scaleY, 1f)
    }

}