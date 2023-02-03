package com.example.androidmentoringprogram.fourthlesson

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.androidmentoringprogram.R
import java.lang.Integer.min

class CustomCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var radius = R.attr.circleRadius.toFloat()

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        paint.color = Color.RED
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
    }

}