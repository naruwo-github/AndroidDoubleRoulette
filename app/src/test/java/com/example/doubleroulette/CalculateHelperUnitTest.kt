package com.example.doubleroulette

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sqrt

class CalculateHelperUnitTest {

    @Test
    fun rotatePoint_isCorrect() {
        val calculateHelper = CalculateHelper()
        val testSet1 = calculateHelper.rotatePoint(1F, 0F, 0F, 0F, (PI/4).toFloat())
        assertEquals(sqrt(2F)/2F, testSet1.first())
        assertEquals(sqrt(2F)/2F, testSet1.last())
    }

}