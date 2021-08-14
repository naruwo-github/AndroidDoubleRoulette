package com.example.doubleroulette

import android.content.Intent
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
            // ルーレット画面へ遷移
            val intent = Intent(this, RouletteActivity::class.java)
            // TODO: ここでルーレットセルのデータを渡す（map<string, array<string>>かな？）
            intent.putExtra("CELL_DATA", "put map data")
            startActivity(intent)
        }
    }

}