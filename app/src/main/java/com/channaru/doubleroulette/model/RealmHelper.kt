package com.channaru.doubleroulette.model

import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class RealmHelper {

    companion object {

        private var singletonRealmInstance: Realm = Realm.getDefaultInstance()

        /**
         * 全てのルーレットデータを返す関数
         */
        fun getAllRouletteData(): RealmResults<DoubleRouletteModel> {
            return singletonRealmInstance.where<DoubleRouletteModel>().findAll()
        }

        /**
         * 全ての『内側』ルーレットデータを返す関数
         */
        fun getInnerRouletteData(): RealmResults<DoubleRouletteModel> {
            return singletonRealmInstance.where<DoubleRouletteModel>().equalTo("isInner", true).findAll()
        }

        /**
         * 全ての『外側』ルーレットデータを返す関数
         */
        fun getOuterRouletteData(): RealmResults<DoubleRouletteModel> {
            return singletonRealmInstance.where<DoubleRouletteModel>().equalTo("isInner", false).findAll()
        }

        /**
         * 新たなルーレットセルデータを生成する関数
         * 既存のデータの中のidの最大値+1が新たなデータのid（既存データがない場合はidは1）
         * @property colorHelper ルーレットセルの色をよしなに設定してくれるクラスのインスタンス
         */
        fun createNewRoulette(colorHelper: ColorHelper) {
            singletonRealmInstance.executeTransaction { db: Realm ->
                val maxId = db.where<DoubleRouletteModel>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1L
                val newCell = db.createObject<DoubleRouletteModel>(nextId)

                val initialColor = colorHelper.returnColorRGBString(db.where<DoubleRouletteModel>().count().toInt())
                newCell.itemColorR = initialColor[0]
                newCell.itemColorG = initialColor[1]
                newCell.itemColorB = initialColor[2]
            }
        }

        /**
         * 全てのルーレットデータを削除する関数
         */
        fun clearData() {
            singletonRealmInstance.executeTransaction { db: Realm ->
                db.where<DoubleRouletteModel>().findAll().deleteAllFromRealm()
            }
        }

        /**
         * idに該当するルーレットセルを削除する関数
         * @property id セルを一意に特定するID
         */
        fun deleteDataById(id: Long) {
            singletonRealmInstance.executeTransaction { db: Realm ->
                db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()?.deleteFromRealm()
            }
        }

        /**
         * ルーレットセルの外側/内側Switchの情報を保存する関数
         * @property id セルを一意に特定するID
         * @property isChecked Switchの値
         */
        fun updateSwitchById(id: Long, isChecked: Boolean) {
            singletonRealmInstance.executeTransaction { db: Realm ->
                val roulette = db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()
                roulette?.isInner = isChecked
            }
        }

        /**
         * ルーレットセルのラベルテキストの情報を保存する関数
         * @property id セルを一意に特定するID
         * @property text ラベルテキストの値
         */
        fun updateTextById(id: Long, text: String) {
            singletonRealmInstance.executeTransaction { db: Realm ->
                val roulette = db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()
                roulette?.itemName = text
            }
        }

        /**
         * 16進数のカラーコード（hexString）を保存する関数
         * @property id セルを一意に特定するID
         * @property r RGBのR値
         * @property g RGBのG値
         * @property b RGBのB値
         */
        fun updateColorById(id: Long, r: String, g: String, b: String) {
            singletonRealmInstance.executeTransaction { db: Realm ->
                val roulette = db.where<DoubleRouletteModel>().equalTo("id", id).findFirst()
                roulette?.itemColorR = r
                roulette?.itemColorG = g
                roulette?.itemColorB = b
            }
        }

    }

}