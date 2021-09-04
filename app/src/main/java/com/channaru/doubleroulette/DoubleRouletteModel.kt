package com.channaru.doubleroulette

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// RealmObjectを継承したクラス
// データを格納するモデルのクラス
open class DoubleRouletteModel : RealmObject() {
    @PrimaryKey
    var id: Long = 0                // プライマリキー
    var isInner: Boolean = false    // ルーレット要素が内側かどうかを示すフラグ
    var itemName: String = "item"   // ルーレット要素の名前
    var itemColorR: String = "ff"   // ルーレット要素の色（hex:Red）
    var itemColorG: String = "00"   // ルーレット要素の色（hex:Green）
    var itemColorB: String = "00"   // ルーレット要素の色（hex:Blue）
}