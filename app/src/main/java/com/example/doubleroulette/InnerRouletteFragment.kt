package com.example.doubleroulette

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm

class InnerRouletteFragment : Fragment() {

    private lateinit var realm: Realm
    private lateinit var pieChartView: PieChartView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pieChartView = PieChartView(this.requireContext())
        // TODO: ここでルーレット画面を整形して描画
        return pieChartView
    }

}