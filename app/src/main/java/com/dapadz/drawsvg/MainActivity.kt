package com.dapadz.drawsvg

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dapadz.drawsvg.view.AndroidSvgView
import com.dapadz.drawsvg.view.parser.AndroidSvgParser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val svg1 = findViewById<AndroidSvgView>(R.id.svg1)
        val svg2 = findViewById<AndroidSvgView>(R.id.svg2)

        svg1.setSvgFile(com.dapadz.drawsvg.view.R.raw.tooth_vita)
        svg1.setTintGradientWithHighlight(
            topColor = getColor(R.color.test1),
            bottomColor = getColor(R.color.test2)
        )
        svg2.setSvgDrawable(com.dapadz.drawsvg.view.R.drawable.ic_straighten)
        svg2.setTint(R.color.black)
    }
}