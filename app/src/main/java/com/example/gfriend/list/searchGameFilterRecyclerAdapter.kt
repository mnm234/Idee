package com.example.gfriend.list

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gfriend.R
import com.example.gfriend.profile.SearchGameFilter
import com.example.gfriend.readImage
import com.google.firebase.storage.FirebaseStorage

class SearchGameFilterRecyclerAdapter(
    private val context: Context,
    private val itemClickListener: SearchGameFilterRecyclerViewHolder.ItemClickListener,
    private val itemList: MutableList<SearchGameFilter>
) : RecyclerView.Adapter<SearchGameFilterRecyclerViewHolder>() {
    override fun onBindViewHolder(holder: SearchGameFilterRecyclerViewHolder, position: Int) {
        holder.let {
            var storage: FirebaseStorage = FirebaseStorage.getInstance()
            var storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
            var pathReference = storageRef.child("gameIcon/${itemList[position].FilterGameId}.png")
            var a = it
//            pathReference.downloadUrl.addOnSuccessListener {
//                Log.d("ぱ",it.toString())
//                Glide.with(this.context)
//                    .load(it.toString())
//                    .into(a.itemImageIcon)
//            }
            it.itemImageIcon.setImageBitmap(readImage("${itemList[position].FilterGameId}.png",context))

//            it.itemTextView.text = itemList[position].profGameFriendCode
//            it.itemImageView.setImageResource(R.mipmap.ic_launcher)
        }
//        holder.itemImageIcon.setOnClickListener {
//            Log.d("wahaha", itemList[position].toString())
//        }
    }

    private var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

//    override fun onBindViewHolder(holder: SearchGameFilterRecyclerViewHolder, position: Int) {
//        holder.let {
//            var storage: FirebaseStorage = FirebaseStorage.getInstance()
//            var storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
//            var pathReference = storageRef.child("gameIcon/${itemList[position].FilterGameId}.png")
//            var a = it
//            pathReference.downloadUrl.addOnSuccessListener {
//                Glide.with(this.context)
//                    .load(it)
//                    .into(a.itemImageView)
//            }
//
////            it.itemTextView.text = itemList[position].profGameFriendCode
////            it.itemImageView.setImageResource(R.mipmap.ic_launcher)
//        }
//    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchGameFilterRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item_filter, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.filterOnItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return SearchGameFilterRecyclerViewHolder(mView)
    }

}