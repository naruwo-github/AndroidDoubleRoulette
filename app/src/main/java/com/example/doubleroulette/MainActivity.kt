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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupAd()
        setupAddButton()
        setupToRouletteButton()
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

    // STARTボタンの設定
    private fun setupToRouletteButton() {
        binding.toRouletteButton.setOnClickListener {
            // ルーレット画面へ遷移
            val intent = Intent(this, RouletteActivity::class.java)
            // TODO: ここでルーレットセルのデータを渡す（map<string, array<string>>かな？）
            intent.putExtra("CELL_DATA", "put map data")
            startActivity(intent)
        }
    }

}