package com.dapadz.drawsvg.view.parser

import com.dapadz.drawsvg.core.form.PathForm
import com.dapadz.drawsvg.core.form.SvgForm
import com.dapadz.drawsvg.view.AndroidSvg
import org.xmlpull.v1.XmlPullParser

internal class XmlVectorParser {

    fun parse(parser: XmlPullParser): AndroidSvg {
        val forms = mutableListOf<SvgForm?>()
        var width = 0
        var height = 0
        var depth = 0
        while (true) {
            val event = parser.next()
            when (event) {
                XmlPullParser.START_DOCUMENT -> { /* no-op */ }
                XmlPullParser.START_TAG -> {
                    depth++
                    when (parser.name) {
                        "vector" -> {
                            width = parser.attrInt("android:viewportWidth") ?: 0
                            height = parser.attrInt("android:viewportHeight") ?: 0
                        }
                        "path" -> createPathForm(parser).also(forms::add)
                        // TODO: "group", "clip-path", "gradient" и пр.
                    }
                }
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.END_DOCUMENT -> break
            }
            if (event == XmlPullParser.END_DOCUMENT) break
        }
        return AndroidSvg(
            width = width,
            height = height,
            forms = forms.filterNotNull()
        )
    }

    private fun createPathForm(parser: XmlPullParser): SvgForm? {
        val d = parser.attr("android:pathData") ?: return null
        val fill = parser.attr("android:fillColor")
        val fillAlpha = parser.attrFloat("android:fillAlpha")
        val stroke = parser.attr("android:strokeColor")
        val strokeWidth = parser.attrFloat("android:strokeWidth")
        val strokeAlpha = parser.attrFloat("android:strokeAlpha")
        return PathForm(
            path = d,
            fillColor = fill,
            fillAlpha = fillAlpha,
            strokeColor = stroke,
            strokeAlpha = strokeAlpha,
            strokeWidth = strokeWidth
        )
    }

    private fun XmlPullParser.attr(name: String): String? =
        (0 until attributeCount)
            .firstOrNull {
                getAttributeName(it) == name || getAttributeName(it).endsWith(
                    name.substringAfter(':')
                )
            }
            ?.let { getAttributeValue(it) }

    private fun XmlPullParser.attrInt(name: String): Int? = attr(name)?.toFloatOrNull()?.toInt()

    private fun XmlPullParser.attrFloat(name: String): Float? = attr(name)?.toFloatOrNull()
}