package com.example.doubleroulette

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View

class ArrowView(context: Context) : View(context) {

    private val paint = Paint()
    private val path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            val viewHalfWidth = width.toFloat() / 2
            val viewHalfHeight = height.toFloat() / 2
            drawArrowTop(it, viewHalfWidth, viewHalfHeight)
            drawArrowBottom(it, viewHalfWidth, viewHalfHeight)
        }
    }

    private fun drawArrowTop(canvas: Canvas, halfWidth: Float, halfHeight: Float) {
        // 画面上部の矢印を描画する（外側ルーレットを指す）
        val firstY = halfHeight / 2
        val secondX = halfWidth - 50
        val secondY = halfHeight / 8
        val thirdX = halfWidth + 50
        val thirdY = halfHeight / 8
        path.moveTo(halfWidth, firstY)
        path.lineTo(secondX, secondY)
        path.lineTo(thirdX, thirdY)
        path.lineTo(halfWidth, firstY)
        canvas.drawPath(path, paint)
    }

    private fun drawArrowBottom(canvas: Canvas, halfWidth: Float, halfHeight: Float) {
        // 画面下部の矢印を描画する（内側ルーレットを指す）
        val firstY = halfHeight * 5 / 4
        val secondX = halfWidth - 50
        val secondY = halfHeight * 15 / 8
        val thirdX = halfWidth + 50
        val thirdY = halfHeight * 15 / 8
        path.moveTo(halfWidth, firstY)
        path.lineTo(secondX, secondY)
        path.lineTo(thirdX, thirdY)
        path.lineTo(halfWidth, firstY)
        canvas.drawPath(path, paint)
    }

}