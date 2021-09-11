package com.channaru.doubleroulette

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.channaru.doubleroulette.model.DoubleRouletteModel
import io.realm.Realm
import io.realm.kotlin.where

class InnerRouletteFragment : Fragment() {

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
        val innerRouletteData = realm.where<DoubleRouletteModel>().equalTo("isInner", true).findAll()
        innerRouletteData?.let {
            if (it.count() > 0) {
                return PieChartView(this.requireContext(), it, true)
            }
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}