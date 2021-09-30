package com.channaru.doubleroulette.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.channaru.doubleroulette.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ResultDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_dialog)
    }

    fun setupResultLabel(resultLabel: String) {
        // UIの更新はメインスレッドでないと行えないため、メインスレッドを指定（呼び出し元が非メインスレッドのため）
        GlobalScope.launch(Dispatchers.Main) {
            findViewById<TextView>(R.id.modalText).text = resultLabel
        }
    }

}