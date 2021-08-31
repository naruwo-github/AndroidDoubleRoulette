package com.example.doubleroulette

import android.graphics.Color
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

    // キーボードを非表示にする処理
    private var hiddenKeyboardListener: (() -> Unit)? = null
    fun setOnHideKeyboardListener(listener: () -> Unit) {
        hiddenKeyboardListener = listener
    }

    // スイッチの状態を保存する処理
    private var updateSwitchListener: ((Long, Boolean) -> Unit)? = null
    fun setOnUpdateSwitchListener(listener: (Long, Boolean) -> Unit) {
        updateSwitchListener = listener
    }

    // テキストの状態を保存する処理
    private var updateTextListener: ((Long, CharSequence) -> Unit)? = null
    fun setOnUpdateTextListener(listener: (Long, CharSequence) -> Unit) {
        updateTextListener = listener
    }

    // カラーピッカーを開く処理
    private var openColorPickerListener: ((Long) -> Unit)? = null
    fun setOnOpenColorPickerListener(listener: (Long) -> Unit) {
        openColorPickerListener = listener
    }

    // セルの削除処理をする関数
    private var deleteListener: ((Long) -> Unit)? = null
    fun setDeleteListener(listener: (Long) -> Unit) {
        deleteListener = listener
    }

    // RecyclerView高速化のためのテクニック
    init {
        // データセット内の項目に一位の識別子があるかどうかを設定する
        // 今回はモデルクラスに一意なidプロパティがあるのでture
        setHasStableIds(true)
    }

    // セルに使用するビューを保持するためのクラス
    // セル1行分のレイアウトを定義するクラス
    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
        val isInnerSwitch: Switch = cell.findViewById(R.id.isInnerSwitch)   // 内側かどうかを表すタイプのスイッチ
        val itemNameText: TextView = cell.findViewById(R.id.itemNameText)   // ルーレット要素の名前のビュー
        val colorButton: Button = cell.findViewById(R.id.colorButton)       // ルーレット要素の色のボタン
        val deleteButton: Button = cell.findViewById(R.id.deleteButton)     // セルの削除ボタン
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoubleRouletteModelAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.roulette_cell, parent, false)
        return ViewHolder(view)
    }

    // 1行分のViewHolderの詳細設定をする関数
    override fun onBindViewHolder(holder: DoubleRouletteModelAdapter.ViewHolder, position: Int) {
        // 循環呼び出しを防ぐためのリスナ初期化
        holder.itemView.setOnClickListener(null)
        holder.isInnerSwitch.setOnCheckedChangeListener(null)
        holder.itemNameText.setOnClickListener(null)
        holder.colorButton.setOnClickListener(null)
        holder.deleteButton.setOnClickListener(null)

        // データの取得：データがない場合はアーリーリターン
        val roulette: DoubleRouletteModel = getItem(position) ?: return

        holder.isInnerSwitch.isChecked = roulette.isInner == true
        holder.itemNameText.text = roulette.itemName
        val hexColorText = "#" + roulette.itemColorR + roulette.itemColorG + roulette.itemColorB
        holder.colorButton.text = hexColorText
        holder.colorButton.setBackgroundColor(Color.parseColor(hexColorText))

        holder.itemView.setOnClickListener {
            hiddenKeyboardListener?.invoke()
        }

        holder.isInnerSwitch.setOnCheckedChangeListener { _, isChecked ->
            // スイッチの状態を更新する処理を呼ぶ
            updateSwitchListener?.invoke(roulette.id, isChecked)
        }

        holder.itemNameText.setOnClickListener {
            // テキスト更新処理を呼ぶ
            updateTextListener?.invoke(roulette.id, holder.itemNameText.text)
        }

        holder.colorButton.setOnClickListener {
            // カラーピッカーを表示させる処理を呼ぶ
            openColorPickerListener?.invoke(roulette.id)
        }

        holder.deleteButton.setOnClickListener {
            // 単体のセルを削除する処理を呼ぶ
            deleteListener?.invoke(roulette.id)
        }

    }

    // RecyclerView高速化のためのテクニック
    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }

    override fun getItemCount(): Int {
        return data?.count() ?: 0
    }

}