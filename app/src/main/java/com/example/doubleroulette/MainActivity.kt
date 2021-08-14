package com.example.doubleroulette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doubleroulette.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.addButton.setOnClickListener {
            // TODO: ボタン押下時の処理を追記（セルの追加）
        }

        binding.toRouletteButton.setOnClickListener {
            // TODO: ボタン押下時の処理を追記（ルーレット画面へ遷移）
        }
    }

}