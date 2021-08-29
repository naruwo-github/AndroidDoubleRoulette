package com.example.doubleroulette

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import io.realm.Realm
import io.realm.kotlin.where

class OuterRouletteFragment : Fragment() {

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val outerRouletteData = realm.where<DoubleRouletteModel>().equalTo("isInner", false).findAll()
        outerRouletteData?.let {
            val metrics = this.requireActivity().windowManager.currentWindowMetrics
            return PieChartView(this.requireContext(), it, metrics.bounds.width().toFloat() / 2)
        }
        return null
    }

}