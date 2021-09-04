package com.channaru.doubleroulette

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

// Applicationクラスはアクティビティよりも先に呼ばれる
// このApplicationクラスを継承したクラスのonCreate内で、Realmデータベースの設定処理を行う
class DoubleRouletteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true).build()
        Realm.setDefaultConfiguration(config)
    }

}