package com.example.doubleroulette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doubleroulette.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.realm.Realm
import io.realm.kotlin.createObject
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

    private fun setupAd() {
        MobileAds.initialize(this) {}
        bottomBannerAdView = findViewById(R.id.bottomBannerAdView)
        val adRequest = AdRequest.Builder().build()
        bottomBannerAdView.loadAd(adRequest)
    }

    private fun setupAddButton() {
        binding.addButton.setOnClickListener {
            realm.executeTransaction { db: Realm ->
                val maxId = db.where<DoubleRouletteModel>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1L
                db.createObject<DoubleRouletteModel>(nextId)
            }
        }
    }

    private fun setupClearButton() {
        binding.clearButton.setOnClickListener {
            realm.executeTransaction { db: Realm ->
                db.deleteAll()
            }
        }
    }

    private fun setupToRouletteButton() {
        binding.toRouletteButton.setOnClickListener {
            val intent = Intent(this, RouletteActivity::class.java)
            startActivity(intent)
        }
    }

}