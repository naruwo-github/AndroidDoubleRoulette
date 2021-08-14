package com.example.doubleroulette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doubleroulette.databinding.ActivityRouletteBinding

class RouletteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouletteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRouletteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // ルーレットセルデータを取得
        val cellData = intent.getStringExtra("CELL_DATA")
        // TODO: セルデータを活用する処理を追記

        binding.backButton.setOnClickListener { finish() }
    }
}