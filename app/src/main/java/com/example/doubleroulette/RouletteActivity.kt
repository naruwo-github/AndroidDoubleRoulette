package com.example.doubleroulette

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doubleroulette.databinding.ActivityRouletteBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import io.realm.Realm
import io.realm.kotlin.where

class RouletteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouletteBinding
    private lateinit var player: MediaPlayer
    private lateinit var bottomBannerAdView: AdView
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()

        initView()

        setupAd()
        setupBackButton()
        setupStartButton()
        setupRoulette()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
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

    private fun setupRoulette() {
        // ルーレットデータを取得
        val roulette = realm.where<DoubleRouletteModel>().findAll()

        // TODO: ルーレットの描画処理を行う
//        drawRoulette(outerItem, innerItem, outerColor, innerColor)
    }

    private fun drawRoulette(outerItem: Array<String>?, innerItem: Array<String>?, outerColor: Array<String>?, innerColor: Array<String>?) {
        // TODO: ルーレットの表示処理を追記するべし
        // TODO: nullチェックを必ずすべし
    }

}