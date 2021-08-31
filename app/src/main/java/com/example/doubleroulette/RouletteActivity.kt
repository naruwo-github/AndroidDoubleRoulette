package com.example.doubleroulette

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.example.doubleroulette.databinding.ActivityRouletteBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class RouletteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRouletteBinding
    private lateinit var player: MediaPlayer
    private lateinit var bottomBannerAdView: AdView
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setupAd()
        setupHandler()
        setupBackButton()
        setupStartButton()
    }

    // 本Viewの初期化処理
    private fun initView() {
        binding = ActivityRouletteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupHandler() {
        handler = Handler(Looper.getMainLooper())
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
        // この音楽ファイルは、再生に4秒+1秒かかる
        player = MediaPlayer.create(this, R.raw.roulette_sound)
        binding.startButton.setOnClickListener {
            player.start()
            // 音が鳴り止むまで4秒+1秒間イベント受付を無効化する
            window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

            // 外側のルーレットを一回転させるアニメーションを作成
            // TODO: ここの360Fをいい感じにランダムで計算して設定すべし
            val outerRotation = RotateAnimation(
                0F, 360F,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F
            )
            outerRotation.duration = OUTER_ANIMATION_TIME
            // 内側のルーレットを一回転させるアニメーションを作成
            // TODO: ここの-360Fをいい感じにランダムで計算して設定すべし
            val innerRotation = RotateAnimation(
                0F, -360F,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5F
            )
            innerRotation.duration = INNER_ANIMATION_TIME

            handler.postDelayed({
                // 4秒+1秒間後、無効化解除
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            }, INNER_ANIMATION_TIME)

            binding.outerRouletteFragmentView.startAnimation(outerRotation)
            binding.innerRouletteFragmentView.startAnimation(innerRotation)
        }
    }

    companion object {
        // 外側ルーレットの回転時間（4秒）
        private const val OUTER_ANIMATION_TIME = 4000L
        // 内側ルーレットの回転時間（5秒＝音楽ファイルが鳴り止むまでの時間）
        private const val INNER_ANIMATION_TIME = 5000L
    }

}