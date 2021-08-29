package com.example.doubleroulette

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import io.realm.RealmResults


class PieChartView(context: Context, private var rouletteData: RealmResults<DoubleRouletteModel>,
                   radius: Float
) : View(context, null) {

    private val paint = Paint()
    private val rect = RectF(0F, 0F, radius * 2, radius * 2)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val partAngle = 360F / rouletteData.count()
        var endAngle = 360F
        rouletteData.forEach {
            paint.color = Color.parseColor("#${it.itemColorR}${it.itemColorG}${it.itemColorB}")
            canvas!!.drawArc(rect, 0F, endAngle, true, paint)
            endAngle -= partAngle
        }

    }

}