package com.example.doubleroulette

import kotlin.math.cos
import kotlin.math.sin

class CalculateHelper {

    init {
    }

    /**
     * 回転の方向：反時計回り
     * @property x 回転したい点のX座標
     * @property y 回転したい点のY座標
     * @property centerX 回転の中心となる点のX座標
     * @property centerY 回転の中心となる点のX座標
     * @property angle 回転の角度θ（ラジアン：π）
     */
     fun rotatePoint(x: Float, y: Float, centerX: Float, centerY: Float, angle: Float): Set<Float> {
        // 原点に戻す
        val mX = x - centerX
        val mY = y - centerY
        // 回転
        val mXRotated = mX * cos(angle) - mY * sin(angle)
        val mYRotated = mX * sin(angle) + mY * cos(angle)
        // 原点に戻した分を戻す
        val xRotated = mXRotated + centerX
        val yRotated = mYRotated + centerY
        return setOf(xRotated, yRotated)
    }

}