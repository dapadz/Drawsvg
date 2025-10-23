package com.dapadz.drawsvg.view.parser

import com.dapadz.drawsvg.core.form.PathForm
import com.dapadz.drawsvg.core.form.SvgForm
import com.dapadz.drawsvg.view.AndroidSvg
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import kotlin.text.trim

internal class DomSvgParser {

    fun parse(document: Document): AndroidSvg {
        val root = document.documentElement
        val (w, h) = extractSize(root)
        val forms = mutableListOf<SvgForm?>()
        val nodes = root.childNodes
        for (i in 0 until nodes.length) {
            val node = nodes.item(i)
            when {
                node.checkNode("path") -> createPathForm(node).also(forms::add)
                // TODO: поддержать <g>, <rect>, <circle>, <line>, <polyline>, <polygon>, <text> и т.д.
            }
        }

        return AndroidSvg(width = w, height = h, forms = forms.filterNotNull())
    }

    private fun createPathForm(node: Node): SvgForm? {
        val el = node as Element
        val d = el.getSafeAttr("d") ?: return null
        val fill = el.getSafeAttr("fill")
        val fillAlpha = el.getSageFloatAttr("fill-opacity")
        val stroke = el.getSafeAttr("stroke")
        val strokeWidth = el.getSageFloatAttr("stroke-width")
        val strokeAlpha = el.getSageFloatAttr("stroke-opacity")
        return PathForm(
            path = d,
            fillColor = fill,
            fillAlpha = fillAlpha,
            strokeColor = stroke,
            strokeAlpha = strokeAlpha,
            strokeWidth = strokeWidth
        )
    }

    private fun Node.checkNode(name: String): Boolean {
        return nodeType == Node.ELEMENT_NODE && nodeName.equals(name, true)
    }

    private fun Element.getSafeAttr(name: String): String? {
        return getAttribute(name).ifBlank { null }
    }

    private fun Element.getSageFloatAttr(name: String): Float? {
        return getSafeAttr(name)?.toFloatOrNull()
    }

    private fun extractSize(svgElement: Element): Pair<Int, Int> {
        val widthAttr = svgElement.getAttribute("width")
        val heightAttr = svgElement.getAttribute("height")
        val vb = svgElement.getAttribute("viewBox")

        val width = widthAttr.toFloatOrNull()?.toInt()
        val height = heightAttr.toFloatOrNull()?.toInt()

        if (width != null && height != null) return width to height

        // Если width/height нет берём из viewBox: "minX minY width height"
        if (!vb.isNullOrBlank()) {
            val parts = vb.trim().split(Regex("\\s+|,")).mapNotNull { it.toFloatOrNull() }
            if (parts.size >= 4) {
                val vbWidth = parts[2].toInt()
                val vbHeight = parts[3].toInt()
                return vbWidth to vbHeight
            }
        }
        return (width ?: 0) to (height ?: 0)
    }
}
