package com.channaru.doubleroulette

import android.content.Context
import android.graphics.*
import android.view.View
import com.channaru.doubleroulette.model.CalculateHelper
import com.channaru.doubleroulette.model.DoubleRouletteModel
import io.realm.RealmResults


class PieChartView(
    context: Context, private var rouletteData: RealmResults<DoubleRouletteModel>,
    private val isInner: Boolean
) : View(context, null) {

    // ルーレットセル一つ分の角度（＊ラジアンではないことに注意）
    private val partAngle = 360F / rouletteData.count()
    private val paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawRouletteCell(it)
            drawRouletteLabel(it)
        }
    }

    private fun drawRouletteCell(canvas: Canvas) {
        val radius = width.toFloat() / 2
        var endAngle = 360F
        val rect = RectF(0F, 0F, radius * 2, radius * 2)
        rouletteData.forEach {
            paint.color = Color.parseColor("#${it.itemColorR}${it.itemColorG}${it.itemColorB}")
            canvas.drawArc(rect, 0F, endAngle, true, paint)
            endAngle -= partAngle
        }
    }

    private fun drawRouletteLabel(canvas: Canvas) {
        val radius = width.toFloat() / 2
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.BLACK
        paint.textSize = 40F
        // ラベルのX座標：内側の場合は radius*3/2 の位置、外側の場合は radius*7/4 の位置
        val labelX = if (isInner) radius * 3 / 2 else radius * 7 / 4
        val labelY = radius
        var radian = CalculateHelper().radianFromDegree(-partAngle / 2) // 反時計回りを表現するため負
        rouletteData.forEach {
            val labelPoint =
                CalculateHelper().rotatePoint(labelX, labelY, radius, radius, radian)
            canvas.drawText(it.itemName, labelPoint.first() - radius / 8/*ラベルを若干左にずらすため-rad/8*/, labelPoint.last(), paint)
            radian += CalculateHelper().radianFromDegree(-partAngle) // 反時計回りを表現するため負
        }
    }

}