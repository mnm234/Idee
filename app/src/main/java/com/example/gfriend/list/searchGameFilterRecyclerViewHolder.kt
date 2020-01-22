package com.example.gfriend.list

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gfriend.R

class SearchGameFilterRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun filterOnItemClick(view: View, position: Int){
        }
    }

    val itemImageIcon: ImageView = view.findViewById(R.id.gameFilterIcon)

    init {
        // layoutの初期設定するときはココ
    }

}