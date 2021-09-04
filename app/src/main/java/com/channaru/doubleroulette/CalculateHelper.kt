package com.channaru.doubleroulette

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class CalculateHelper {

    /**
     * 回転の方向：反時計回り
     * @property x 回転したい点のX座標
     * @property y 回転したい点のY座標
     * @property centerX 回転の中心となる点のX座標
     * @property centerY 回転の中心となる点のX座標
     * @property radian 回転の角度θ（ラジアン：π）
     * @return サイズが2のSet<Float>
     */
     fun rotatePoint(x: Float, y: Float, centerX: Float, centerY: Float, radian: Float): Set<Float> {
        // 原点に戻す
        val mX = x - centerX
        val mY = y - centerY
        // 回転
        val mXRotated = mX * cos(radian) - mY * sin(radian)
        val mYRotated = mX * sin(radian) + mY * cos(radian)
        // 原点に戻した分を戻す
        val xRotated = mXRotated + centerX
        val yRotated = mYRotated + centerY
        return setOf(xRotated, yRotated)
    }

    /**
     * @property degree 角度
     * @return degreeをラジアンに変換した値
     */
    fun radianFromDegree(degree: Float): Float {
        return (degree * PI / 180).toFloat()
    }

}