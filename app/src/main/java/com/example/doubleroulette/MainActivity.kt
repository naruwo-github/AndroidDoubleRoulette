package com.example.doubleroulette

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.view.allViews
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doubleroulette.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog

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

    // 「RecyclerView、各種ボタン、セル以外のView」をタップした際にキーボード非表示
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        hideKeyboard()
        return false
    }

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

        // RecyclerViewのセルをタップした時のイベントリスナー
        adapter.setOnHideKeyboardListener {
            hideKeyboard()
        }

        // 単一のセルのスイッチの状態を更新する処理を設定
        adapter.setOnUpdateSwitchListener { id, isChecked ->
            // TODO: 【バグ修正】連続でスイッチ押すとアプリが落ちる
            updateSwitchById(id, isChecked)
        }

        // 単一のセルのテキストの状態を更新する処理を設定
        adapter.setOnUpdateTextListener { id, text ->
            // TODO: 【バグ修正】ふたもじ入れるとアプリが落ちる
            updateTextById(id, text)
        }

        // カラーピッカーを開く処理を設定
        adapter.setOnOpenColorPickerListener { id: Long ->
            openColorPicker { color: Int ->
                var r = Integer.toHexString(color.red)
                if (r == "0") r = "00"
                var g = Integer.toHexString(color.green)
                if (g == "0") g = "00"
                var b = Integer.toHexString(color.blue)
                if (b == "0") b = "00"
                updateColorById(id, r, g, b)
            }
        }

        // 単一のセルを削除する処理を設定
        adapter.setDeleteListener { id ->
            deleteDataById(id)
        }

    }

    private fun updateSwitchById(id: Long, isChecked: Boolean) {
        realm.executeTransaction { db: Realm ->
            val roulette = db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()
            roulette?.isInner = isChecked
        }
    }

    private fun updateTextById(id: Long, text: String) {
        realm.executeTransaction { db: Realm ->
            val roulette = db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()
            roulette?.itemName = text
        }
    }

    // 16進数のカラーコード（hexString）を保存する処理
    private fun updateColorById(id: Long, r: String, g: String, b: String) {
        realm.executeTransaction { db: Realm ->
            val roulette = db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()
            roulette?.itemColorR = r
            roulette?.itemColorG = g
            roulette?.itemColorB = b
        }
    }

    private fun deleteDataById(id: Long) {
        realm.executeTransaction { db: Realm ->
            db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()?.deleteFromRealm()
        }
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
                db.where<DoubleRouletteModel>().findAll().deleteAllFromRealm()
            }
        }
    }

    private fun setupToRouletteButton() {
        binding.toRouletteButton.setOnClickListener {
            val intent = Intent(this, RouletteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(
                    it.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
        }
        // フォーカスアウト処理
        binding.recyclerView.allViews.forEach {
            it.clearFocus()
        }
    }

    private fun openColorPicker(callback: (Int) -> Unit) {
        val colorPicker: ColorPickerDialog = ColorPickerDialog.Builder()
            .setInitialColor(Color.RED)     //  set Color Model. ARGB, RGB or HSV
            .setColorModel(ColorModel.HSV)  //  set is user be able to switch color model
            .setColorModelSwitchEnabled(true)
            .setButtonOkText(android.R.string.ok)
            .setButtonCancelText(android.R.string.cancel)
            .onColorSelected { color: Int ->
                callback(color)
            }
            .create()
        colorPicker.show(supportFragmentManager, "color_picker")
    }

}