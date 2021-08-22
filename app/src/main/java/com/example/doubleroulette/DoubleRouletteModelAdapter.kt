package com.example.doubleroulette

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
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
    private var updateSwitchListener: ((Long?, Boolean) -> Unit)? = null
    fun setOnUpdateSwitchListener(listener: (Long?, Boolean) -> Unit) {
        updateSwitchListener = listener
    }

    // テキストの状態を保存する処理
    private var updateTextListener: ((Long?, String) -> Unit)? = null
    fun setOnUpdateTextListener(listener: (Long?, String) -> Unit) {
        updateTextListener = listener
    }

    // 色の状態を保存する処理
    private var updateColorListener: ((Long?, String) -> Unit)? = null
    fun setOnUpdateColorListener(listener: (Long?, String) -> Unit) {
        updateColorListener = listener
    }

    // セルの削除処理をする関数
    private var deleteListener: ((Long?) -> Unit)? = null
    fun setDeleteListener(listener: (Long?) -> Unit) {
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
        // inflateメソッドより、XMLファイルから画面を生成している
        val view = inflater.inflate(R.layout.roulette_cell, parent, false)
        return ViewHolder(view)
    }

    // 1行分のViewHolderの詳細設定をする関数
    override fun onBindViewHolder(holder: DoubleRouletteModelAdapter.ViewHolder, position: Int) {
        val roulette: DoubleRouletteModel? = getItem(position)

        holder.itemView.setOnClickListener {
            hiddenKeyboardListener?.invoke()
            holder.itemNameText.clearFocus() // TODO: これだけだと一個のセルにしかフォーカスアウト処理が乗らない
        }

        holder.isInnerSwitch.isChecked = roulette?.isInner == true
        holder.isInnerSwitch.setOnCheckedChangeListener { _, isChecked ->
            // スイッチの状態を更新する処理を呼ぶ
            updateSwitchListener?.invoke(roulette?.id, isChecked)
        }

        holder.itemNameText.text = roulette?.itemName
        holder.itemNameText.addTextChangedListener {
            it?.let {
                // テキストを更新する処理を呼ぶ
                updateTextListener?.invoke(roulette?.id, holder.itemNameText.text.toString())
            }
        }

        holder.colorButton.text = roulette?.itemColor
        holder.colorButton.setOnClickListener {
            // TODO: ボタンがタップされたタイミングのイベント
            // TODO: ピッカーを呼ぶ処理
            // TODO: ピッカーで選択した色が返ってきたら、ボタン背景色の変更、updateColorListener関数の呼び出しをする
        }

        holder.deleteButton.setOnClickListener {
            // 単体のセルを削除する処理を呼ぶ
            deleteListener?.invoke(roulette?.id)
        }

    }

    // RecyclerView高速化のためのテクニック
    override fun getItemId(position: Int): Long {
        return getItem(position)?.id ?: 0
    }

}