package com.example.doubleroulette

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doubleroulette.databinding.ActivityRouletteBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class RouletteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRouletteBinding
    private lateinit var player: MediaPlayer
    private lateinit var bottomBannerAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setupAd()
        setupBackButton()
        setupStartButton()
    }

    // 本Viewの初期化処理
    private fun initView() {
        binding = ActivityRouletteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupAd() {
        bottomBannerAdView = findViewById(R.id.bottomBannerAdView2)
        val adRequest = AdRequest.Builder().build()
        bottomBannerAdView.loadAd(adRequest)
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupStartButton() {
        // 音楽データの取得
        player = MediaPlayer.create(this, R.raw.roulette_sound)
        // STARTボタンのイベント設定
        binding.startButton.setOnClickListener {
            // ドラムロールを開始
            player.start()
            // TODO: ルーレットを回す処理を追記
        }
    }

}