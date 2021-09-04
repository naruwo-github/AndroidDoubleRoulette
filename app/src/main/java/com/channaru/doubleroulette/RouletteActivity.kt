package com.channaru.doubleroulette

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.channaru.doubleroulette.databinding.ActivityRouletteBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlin.random.Random

class RouletteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRouletteBinding
    private lateinit var player: MediaPlayer
    private lateinit var bottomBannerAdView: AdView
    private lateinit var handler: Handler

    private var fromDegreesOuter = 0F   // 外側ルーレットの開始角度
    private var fromDegreesInner = 0F   // 内側ルーレットの開始角度
    private var toDegreesOuter = 0F     // 外側ルーレットの終了角度
    private var toDegreesInner = 0F     // 内側ルーレットの終了角度

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
        bottomBannerAdView = findViewById(R.id.rouletteBottomBannerAdView)
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
            // ルーレットが止まるまで、イベント検知を無効
            preventEventWhileRotating()

            // 2~5回転のうちでランダムな回転角度を設定
            val toDegrees = 360F * (2F + 3F * Random.nextFloat())
            toDegreesOuter += toDegrees
            toDegreesInner -= toDegrees

            // 回転アニメーションを開始
            binding.outerRouletteFragmentView.startAnimation(makeRotation(fromDegreesOuter, toDegreesOuter, OUTER_ANIMATION_TIME))
            binding.innerRouletteFragmentView.startAnimation(makeRotation(fromDegreesInner, toDegreesInner, INNER_ANIMATION_TIME))

            // 回転角度を保存して更新
            fromDegreesOuter = toDegreesOuter
            fromDegreesInner = toDegreesInner
        }
    }

    private fun preventEventWhileRotating() {
        // 音が鳴り止むまで4秒+1秒間イベント受付を無効化する
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        handler.postDelayed({
            // 4秒+1秒間後、無効化解除
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }, INNER_ANIMATION_TIME)
    }

    private fun makeRotation(fromDegrees: Float, toDegrees: Float, duration: Long): RotateAnimation {
        val rotation = RotateAnimation(
            fromDegrees, toDegrees,
            Animation.RELATIVE_TO_SELF, 0.5F,
            Animation.RELATIVE_TO_SELF, 0.5F
        )
        rotation.duration = duration
        rotation.fillAfter = true
        return rotation
    }

    companion object {
        // 外側ルーレットの回転時間（4秒）
        private const val OUTER_ANIMATION_TIME = 4000L
        // 内側ルーレットの回転時間（5秒＝音楽ファイルが鳴り止むまでの時間）
        private const val INNER_ANIMATION_TIME = 5000L
    }

}