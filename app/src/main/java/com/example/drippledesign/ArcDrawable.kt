package com.example.drippledesign

import android.graphics.*
import android.graphics.drawable.Drawable

class ArcDrawable : Drawable() {

    private val horizontalSpace = 60f
    private val rectangle = RectF(60f, 60f, 1000f, 300f)
    private val strokeWidth = 6f
    private val startAngle = 0f
    private val sweepAngle = -180f
    private val s = strokeWidth / 2

    private val grayPaint: Paint = Paint().apply {
        setARGB(255, 255, 0, 0)
        isAntiAlias = true
        color = 0x40000000
        style = Paint.Style.FILL
    }

    private val pathRect: RectF = RectF().apply {
        left = rectangle.left - s
        right = rectangle.right + s
        top = rectangle.top - s
        bottom = rectangle.bottom + s
    }
    private val path = Path().apply {
        arcTo(pathRect, startAngle, sweepAngle)
        pathRect.left = rectangle.left + s
        pathRect.right = rectangle.right - s
        pathRect.top = rectangle.top + s
        pathRect.bottom = rectangle.bottom - s
        arcTo(pathRect, startAngle + sweepAngle, -sweepAngle)
        close()
    }

    override fun draw(canvas: Canvas) {
        // Get the drawable's bounds
        // val width: Int = bounds.width()
        //val height: Int = bounds.height()
        // val radius: Float = min(width, height).toFloat() / 2f

        canvas.drawPath(path, grayPaint)
    }

    override fun setAlpha(alpha: Int) {
        // This method is required
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        // This method is required
    }

    override fun getOpacity(): Int =
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        PixelFormat.TRANSLUCENT
}