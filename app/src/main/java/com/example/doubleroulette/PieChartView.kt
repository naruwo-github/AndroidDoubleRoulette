package com.example.doubleroulette

import android.content.Context
import android.graphics.*
import android.view.View
import io.realm.RealmResults
import kotlin.math.cos
import kotlin.math.sin


class PieChartView(context: Context, private var rouletteData: RealmResults<DoubleRouletteModel>,
                   private val radius: Float, private val isInner: Boolean
) : View(context, null) {

    // ルーレットセル一つ分の角度
    private val partAngle = 360F / rouletteData.count()
    private val paint = Paint()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawRoulette(it)
        }
    }

    private fun drawRoulette(canvas: Canvas) {
        var endAngle = 360F
        val rect = RectF(0F, 0F, radius * 2, radius * 2)
        // ラベルのX座標：内側の場合は radius/2 の位置、外側の場合は radius * 7 / 4 の位置
        val labelX = if (isInner) radius / 2 else radius * 7 / 4
        val labelY = radius
        rouletteData.forEach {
            // ルーレットセルの描画
            paint.color = Color.parseColor("#${it.itemColorR}${it.itemColorG}${it.itemColorB}")
            canvas.drawArc(rect, 0F, endAngle, true, paint)
            endAngle -= partAngle

            // TODO: ラベル描画周りが不適切、要修正
            // ルーレットラベルの追加
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.color = Color.BLACK
            paint.textSize = 40F
            val labelPoint = CalculateHelper().rotatePoint(labelX, labelY, radius, radius, endAngle + partAngle / 2)
            canvas.drawText(it.itemName, labelPoint.first(), labelPoint.last(), paint)
        }
    }

}