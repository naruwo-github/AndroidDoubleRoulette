package com.example.doubleroulette

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.DisplayMetrics
import android.view.View
import kotlin.concurrent.fixedRateTimer

class ArrowView(context: Context) : View(context) {

    private val paint = Paint()
    private val path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            paint.color = Color.BLACK
            paint.style = Paint.Style.FILL
            drawArrowTop(it)
            drawArrowBottom(it)
        }
    }

    private fun drawArrowTop(canvas: Canvas) {
        val viewHalfWidth = width.toFloat() / 2
        val viewHalfHeight = height.toFloat() / 2
        // 画面上部の矢印を描画する（外側ルーレットを指す）
        val firstX = viewHalfWidth
        val firstY = viewHalfHeight / 2
        val secondX = viewHalfWidth - 50
        val secondY = viewHalfHeight / 8
        val thirdX = viewHalfWidth + 50
        val thirdY = viewHalfHeight / 8

        path.moveTo(firstX, firstY)
        path.lineTo(secondX, secondY)
        path.lineTo(thirdX, thirdY)
        path.lineTo(firstX, firstY)
        canvas.drawPath(path, paint)
    }

    private fun drawArrowBottom(canvas: Canvas) {
        val viewHalfWidth = width.toFloat() / 2
        val viewHalfHeight = height.toFloat() / 2
        // 画面下部の矢印を描画する（内側ルーレットを指す）
        val firstX = viewHalfWidth
        val firstY = viewHalfHeight * 5 / 4
        val secondX = viewHalfWidth - 50
        val secondY = viewHalfHeight * 15 / 8
        val thirdX = viewHalfWidth + 50
        val thirdY = viewHalfHeight * 15 / 8

        path.moveTo(firstX, firstY)
        path.lineTo(secondX, secondY)
        path.lineTo(thirdX, thirdY)
        path.lineTo(firstX, firstY)
        canvas.drawPath(path, paint)
    }

}