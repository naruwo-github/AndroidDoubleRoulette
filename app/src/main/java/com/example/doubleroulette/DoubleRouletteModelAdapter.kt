package com.example.doubleroulette

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class DoubleRouletteModelAdapter(data: OrderedRealmCollection<DoubleRouletteModel>) : RealmRecyclerViewAdapter<DoubleRouletteModel, DoubleRouletteModelAdapter.ViewHolder>(data, true) {

    // RecyclerView高速化のためのテクニック
    init {
        // データセット内の項目に一位の識別子があるかどうかを設定する
        // 今回はモデルクラスに一意なidプロパティがあるのでture
        setHasStableIds(true)
    }

    // セルに使用するビューを保持するためのクラス
    // セル1行分のレイアウトを定義するクラス
    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val isInnerSwitch: Switch = cell.findViewById(R.id.isInnerSwitch)   // 内側かどうかを表すタイプのスイッチ？セグメントコントロール？
        val itemNameText: TextView = cell.findViewById(R.id.itemNameText)   // ルーレット要素の名前のビュー
        val colorButton: Button = cell.findViewById(R.id.colorButton)       // ルーレット要素の色のビュー
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoubleRouletteModelAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // inflateメソッドより、XMLファイルから画面を生成している
        val view = inflater.inflate(R.layout.roulette_cell, parent, false)
        return ViewHolder(view)
    }

    // 1行分のViewHolderの詳細設定をする関数
    override fun onBindViewHolder(holder: DoubleRouletteModelAdapter.ViewHolder, position: Int) {
        val roulette: DoubleRouletteModel? = getItem(position)
        holder.isInnerSwitch.isChecked = roulette?.isInner == true
        holder.itemNameText.text = roulette?.itemName
        holder.colorButton.text = roulette?.itemColor   // TODO: 背景色を変更する処理に置き換える
    }

    // RecyclerView高速化のためのテクニック
    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }

}