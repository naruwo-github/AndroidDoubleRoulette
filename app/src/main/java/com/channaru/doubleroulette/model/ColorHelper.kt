package com.channaru.doubleroulette.model


class ColorHelper {

    // RGBをhexで持つ変数
    private val colorStock: Array<Array<String>> = arrayOf(
        arrayOf("ff", "00", "00"),// 赤
        arrayOf("ff", "66", "00"),// オレンジ
        arrayOf("ff", "B2", "00"),// 橙
        arrayOf("ff", "ff", "00"),// 黄色
        arrayOf("B2", "ff", "00"),// ライム

        arrayOf("66", "ff", "00"),// 黄緑
        arrayOf("00", "ff", "00"),// 緑
        arrayOf("66", "ff", "ff"),// 水色
        arrayOf("00", "B2", "ff"),// ターコイズ
        arrayOf("00", "00", "ff"),// 青

        arrayOf("33", "00", "CC"),// 青紫
        arrayOf("66", "00", "99"),// 紫
        arrayOf("99", "00", "66"),// 赤紫
        arrayOf("B2", "00", "33"),// 明るい茶色
        arrayOf("80", "00", "00"),// ブラウン
    )

    /**
     * @property cellNumber 新たに追加したルーレットセルが、現状保持している中で何番目のセルであるかを表す値
     * @return サイズ3のArray<String>型のオブジェクト（16進数のRGB文字列を示すもの）
     */
    fun returnColorRGBString(cellNumber: Int): Array<String> {
        return if (cellNumber <= colorStock.count()) {
            colorStock[cellNumber - 1]
        } else {
            colorStock.first()
        }
    }

}