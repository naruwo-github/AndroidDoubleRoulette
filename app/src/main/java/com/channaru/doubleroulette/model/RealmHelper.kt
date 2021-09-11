package com.channaru.doubleroulette.model

import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class RealmHelper {

    companion object {

        private var singletonRealmInstance: Realm = Realm.getDefaultInstance()

        // TODO: インスタンスの取得は原則禁止にし、getInstance()を実装しなくてもいい
        fun getInstance(): Realm {
            return singletonRealmInstance
        }

        // TODO: CRUDを実装し、DBのトランザクションはこのクラスに隠蔽する

        fun getAllRouletteData(): RealmResults<DoubleRouletteModel> {
            return singletonRealmInstance.where<DoubleRouletteModel>().findAll()
        }
    }

}