package com.example.doubleroulette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doubleroulette.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomBannerAdView: AdView

    // TODO: モック↓↓↓
    private val cellItemDataMock: Array<Array<String>> = arrayOf(
        arrayOf("outer1", "outer2", "outer3", "outer4", "outer5"),
        arrayOf("inner1", "inner2", "inner3")
    )
    private val cellColorDataMock: Array<Array<String>> = arrayOf(
        arrayOf("#ff0000", "#00ff00", "#0000ff", "#ffff00", "#ff00ff"),
        arrayOf("#00f0ff", "#f0f0f0", "#0f0f0f")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        setupAd()
        setupAddButton()
        setupClearButton()
        setupToRouletteButton()
    }

    // 本Viewの初期化処理
    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    // AdMobの広告設定
    private fun setupAd() {
        MobileAds.initialize(this) {}
        bottomBannerAdView = findViewById(R.id.bottomBannerAdView)
        val adRequest = AdRequest.Builder().build()
        bottomBannerAdView.loadAd(adRequest)
    }

    // ADDボタンの設定
    private fun setupAddButton() {
        binding.addButton.setOnClickListener {
            // TODO: ボタン押下時の処理を追記（セルの追加）
        }
    }

    // CLEARボタンの設定
    private fun setupClearButton() {
        binding.clearButton.setOnClickListener {
            // TODO: セルデータを全て削除する処理を追記
        }
    }

    // STARTボタンの設定
    private fun setupToRouletteButton() {
        binding.toRouletteButton.setOnClickListener {
            // ルーレット画面へ遷移
            val intent = Intent(this, RouletteActivity::class.java)

            // TODO: ここでルーレットセルのデータを渡す（map<string, array<string>>かな？）
            intent.putExtra("OUTER_CELL_ITEM_DATA", cellItemDataMock[0])
            intent.putExtra("INNER_CELL_ITEM_DATA", cellItemDataMock[1])
            intent.putExtra("OUTER_CELL_COLOR_DATA", cellColorDataMock[0])
            intent.putExtra("INNER_CELL_COLOR_DATA", cellColorDataMock[1])

            startActivity(intent)
        }
    }

}