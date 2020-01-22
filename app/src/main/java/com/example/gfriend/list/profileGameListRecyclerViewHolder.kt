package com.example.gfriend.list

import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gfriend.R

class ProfileRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemTextView: TextView = view.findViewById(R.id.gameListId)
    val itemImageView: ImageView = view.findViewById(R.id.gameListIcon)
    val itemSwitch: Switch = view.findViewById(R.id.bosyu_switch)

    init {
        // layoutの初期設定するときはココ
    }

}