package com.channaru.doubleroulette.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.channaru.doubleroulette.model.RealmHelper

class InnerRouletteFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val innerRouletteData = RealmHelper.getInnerRouletteData()
        innerRouletteData.let {
            if (it.count() > 0) {
                return PieChartView(this.requireContext(), it, true)
            }
        }
        return null
    }

}