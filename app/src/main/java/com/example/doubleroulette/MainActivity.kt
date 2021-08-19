package com.example.doubleroulette

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doubleroulette.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.realm.Realm
import io.realm.kotlin.where

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomBannerAdView: AdView
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()

        initView()
        setupRecyclerView()

        setupAd()
        setupAddButton()
        setupClearButton()
        setupToRouletteButton()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Realmインスタンスの破棄＆開放
        realm.close()
    }

    // 本Viewの初期化処理
    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    // データベースから取得した全てのスケジュールをRecyclerViewに表示する準備
    private fun setupRecyclerView() {
        // データを一列で表示するようにLayoutManagerを設定
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val roulette = realm.where<DoubleRouletteModel>().findAll()
        val adapter = DoubleRouletteModelAdapter(roulette)
        binding.recyclerView.adapter = adapter
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
            startActivity(intent)
        }
    }

}