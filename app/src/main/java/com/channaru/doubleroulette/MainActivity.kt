package com.channaru.doubleroulette

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.view.allViews
import androidx.recyclerview.widget.LinearLayoutManager
import com.channaru.doubleroulette.databinding.ActivityMainBinding
import com.channaru.doubleroulette.model.ColorHelper
import com.channaru.doubleroulette.model.RealmHelper
import com.channaru.doubleroulette.view_model.DoubleRouletteModelAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomBannerAdView: AdView
    private lateinit var colorHelper: ColorHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        colorHelper = ColorHelper()

        initView()
        setupRecyclerView()

        setupAd()
        setupAddButton()
        setupClearButton()
        setupToRouletteButton()
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
        val adapter = DoubleRouletteModelAdapter(RealmHelper.getAllRouletteData())
        binding.recyclerView.adapter = adapter

        // RecyclerViewのセルをタップした時のイベントリスナー
        adapter.setOnHideKeyboardListener {
            hideKeyboard()
        }

        // 単一のセルのスイッチの状態を更新する処理を設定
        adapter.setOnUpdateSwitchListener { id, isChecked ->
            RealmHelper.updateSwitchById(id, isChecked)
        }

        // 単一のセルのテキストの状態を更新する処理を設定
        adapter.setOnUpdateTextListener { id, text ->
            openTextEditDialog(id, text)
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
                RealmHelper.updateColorById(id, r, g, b)
            }
        }

        // 単一のセルを削除する処理を設定
        adapter.setDeleteListener { id ->
            RealmHelper.deleteDataById(id)
        }

    }

    private fun setupAd() {
        MobileAds.initialize(this) {}
        bottomBannerAdView = findViewById(R.id.settingBottomBannerAdView)
        val adRequest = AdRequest.Builder().build()
        bottomBannerAdView.loadAd(adRequest)
    }

    private fun setupAddButton() {
        binding.addButton.setOnClickListener {
            RealmHelper.createNewRoulette(colorHelper)
        }
    }

    private fun setupClearButton() {
        binding.clearButton.setOnClickListener {
            RealmHelper.clearData()
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

    private fun openTextEditDialog(id: Long, text: CharSequence) {
        val textView = EditText(this)
        textView.text = text as Editable?
        AlertDialog.Builder(this)
            .setView(textView)
            .setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
                RealmHelper.updateTextById(id, textView.text.toString())
            })
            .setNegativeButton("CANCEL", null)
            .create()
            .show()
    }

}