package com.channaru.doubleroulette

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.channaru.doubleroulette.databinding.ActivityRouletteBinding
import com.channaru.doubleroulette.model.RealmHelper
import com.channaru.doubleroulette.view.ResultDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlin.random.Random

class RouletteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRouletteBinding
    private lateinit var player: MediaPlayer
    private lateinit var bottomBannerAdView: AdView
    private lateinit var handler: Handler
    private lateinit var dialog: ResultDialog

    private var fromDegreesOuter = 0F   // 外側ルーレットの開始角度
    private var fromDegreesInner = 0F   // 内側ルーレットの開始角度
    private var toDegreesOuter = 0F     // 外側ルーレットの終了角度
    private var toDegreesInner = 0F     // 内側ルーレットの終了角度

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setupAd()
        setupHandlerAndDialog()
        setupBackButton()
        setupStartButton()
    }

    private fun initView() {
        binding = ActivityRouletteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupHandlerAndDialog() {
        handler = Handler(Looper.getMainLooper())
        dialog = ResultDialog(this)
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
            binding.outerRouletteFragmentView.startAnimation(
                makeRotation(
                    fromDegreesOuter,
                    toDegreesOuter,
                    OUTER_ANIMATION_TIME
                )
            )
            binding.innerRouletteFragmentView.startAnimation(
                makeRotation(
                    fromDegreesInner,
                    toDegreesInner,
                    INNER_ANIMATION_TIME
                )
            )

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

            // TODO: insert function getting result labels by rotation angle
            val innerRouletteData = RealmHelper.getInnerRouletteData()
            val innerPieceAngle = -360F / innerRouletteData.count()
            // 針が90度分回転に進んでいるので、減算（TODO: ???この考えがそもそも間違ってる）
            val innerDegree = (fromDegreesInner - 90F) % (-360F)
            var innerResult = ""
            if (innerRouletteData.count() > 0) {
                for (i in 0 until innerRouletteData.count()) {
                    if (i * innerPieceAngle > innerDegree && innerDegree >= (i+1) * innerPieceAngle ) {
                        innerResult = innerRouletteData[i]!!.itemName
                    }
                }
            }

            val outerRouletteData = RealmHelper.getOuterRouletteData()
            val outerPieceAngle = 360F / outerRouletteData.count()
            // 針が90度分回転に遅れているので、加算
            val outerDegree = (fromDegreesOuter + 90F) % 360F
            var outerResult = ""
            if (outerRouletteData.count() > 0) {
                for (i in 0 until outerRouletteData.count()) {
                    if (i * outerPieceAngle <= outerDegree && outerDegree < (i+1) * outerPieceAngle ) {
                        outerResult = outerRouletteData[i]!!.itemName
                    }
                }
            }

            val resultLabel = "Outer: $outerResult\nInner: $innerResult"
            dialog.setupResultLabel(resultLabel)
            dialog.show()
        }, INNER_ANIMATION_TIME)
    }

    private fun makeRotation(
        fromDegrees: Float,
        toDegrees: Float,
        duration: Long
    ): RotateAnimation {
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