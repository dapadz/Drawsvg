package com.dapadz.drawsvg.view.parser

import android.content.Context
import androidx.annotation.RawRes
import com.dapadz.drawsvg.view.AndroidSvg
import org.w3c.dom.Document
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

class AndroidSvgParser() {

    /**
     * Считывает *VectorDrawable XML* из `res/drawable*` и конвертирует в [com.dapadz.drawsvg.view.AndroidSvg].
     * @throws IllegalArgumentException если ресурс не является XML VectorDrawable
     * @throws android.content.res.Resources.NotFoundException если ресурс не найден
     */
    fun fromDrawableRes(context: Context, svg: Int): AndroidSvg {
        val parser = context.resources.getXml(svg)
        return XmlVectorParser().parse(parser)
    }

    /**
     * Считывает XML/ SVG поток из `res/raw`
     * @throws IllegalArgumentException при неподдерживаемом формате
     */
    fun fromRawRes(context: Context, @RawRes svg: Int): AndroidSvg {
        context.resources.openRawResource(svg).use { input ->
            return fromSvgFile(input)
        }
    }

    /**
     * Считывает чистый SVG.
     * @throws IllegalArgumentException если корневой тег не `svg`
     */
    fun fromSvgFile(inputStream: InputStream): AndroidSvg {
        val doc = buildSecureDom(inputStream)
        val root = doc.documentElement
        require(root != null && root.nodeName.equals("svg", ignoreCase = true)) {
            "Expected <svg> root for fromSvgFile()"
        }
        return DomSvgParser().parse(doc)
    }

    private fun buildSecureDom(input: InputStream): Document {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(input)
            .apply {
                normalize()
            }
    }
}