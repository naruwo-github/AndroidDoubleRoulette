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

        binding = ActivityRouletteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // ルーレットセルデータを取得
        val cellData = intent.getStringExtra("CELL_DATA")
        println(cellData) // TODO: デバッグ用コードなので後ほど削除
        // TODO: セルデータを活用する処理を追記

        // 設定画面に戻る処理
        binding.backButton.setOnClickListener { finish() }

        // 音楽データの取得
        player = MediaPlayer.create(this, R.raw.roulette_sound)
        // ルーレットを回す処理
        binding.startButton.setOnClickListener {
            // TODO: ドラムロールを鳴らす処理を追記
            player.start()
            // TODO: ルーレットを回す処理を追記
        }

        // AdMobの初期化処理
        bottomBannerAdView = findViewById(R.id.bottomBannerAdView2)
        val adRequest = AdRequest.Builder().build()
        bottomBannerAdView.loadAd(adRequest)

    }
}