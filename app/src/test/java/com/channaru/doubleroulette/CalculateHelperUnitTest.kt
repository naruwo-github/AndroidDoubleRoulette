package com.channaru.doubleroulette

import com.channaru.doubleroulette.model.CalculateHelper
import org.junit.Test

import org.junit.Assert.*
import kotlin.math.PI
import kotlin.math.sqrt

class CalculateHelperUnitTest {

    @Test
    fun rotatePoint_isCorrect() {
        val calculateHelper = CalculateHelper()
        val testSet1 = calculateHelper.rotatePoint(1F, 0F, 0F, 0F, (PI/4).toFloat())
        assertEquals(sqrt(2F)/2F, testSet1.first())
        assertEquals(sqrt(2F)/2F, testSet1.last())
    }

    @Test
    fun radianFromDegree_isCorrect() {
        val calculateHelper = CalculateHelper()
        val radian = calculateHelper.radianFromDegree(360F)
        assertEquals(2*PI.toFloat(), radian)
    }

}