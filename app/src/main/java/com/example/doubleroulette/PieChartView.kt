package com.example.doubleroulette

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import io.realm.RealmResults


class PieChartView(context: Context, rouletteData: RealmResults<DoubleRouletteModel>, radius: Float) : View(context, null) {

    private val paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 描画を確認するための仮の実装
        paint.color = Color.rgb(255,212,121)
        val strokeWidth: Float = (width / 8).toFloat()
        paint.strokeWidth = strokeWidth
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        val rect = RectF(strokeWidth/2, strokeWidth/2, width.toFloat()-strokeWidth/2, height.toFloat()-strokeWidth/2)
        val rect2 = RectF(0F, 0F, 1F, 1F)
        canvas!!.drawArc(rect2, 0f, 360f, false, paint)
    }

}