package com.example.gfriend.list

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gfriend.CloudFireStore
import com.example.gfriend.R
import com.example.gfriend.profile.ProfileFragment
import com.example.gfriend.profile.ProfilePageInfo
import com.example.gfriend.readImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class SearchGameListRecyclerAdapter(
    private val context: Context,
    private val itemClickListener: SearchRecyclerViewHolder.ItemClickListener,
    private val itemList: MutableList<ProfilePageInfo>
) : RecyclerView.Adapter<SearchRecyclerViewHolder>() {

    private var mRecyclerView: RecyclerView? = null
    private var mAuth: FirebaseAuth? = null
    val db = FirebaseFirestore.getInstance()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }

    override fun onBindViewHolder(holder: SearchRecyclerViewHolder, position: Int) {
        holder.let {
            var storage: FirebaseStorage = FirebaseStorage.getInstance()
            var storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
            var pathReference = storageRef.child("gameIcon/${itemList[position].profGameId}.png")
//            var a = it
//            pathReference.downloadUrl.addOnSuccessListener {
//                Glide.with(this.context)
//                    .load(it)
//                    .into(a.itemImageView)
//            }
            it.itemImageView.setImageBitmap(readImage("${itemList[position].profGameId}.png",context))
//            it.itemSwitch.isChecked = itemList[position].recFlag


//            it.itemSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//                if(isChecked){
////                    Toast.makeText(this.context, isChecked.toString()+itemList[position], Toast.LENGTH_SHORT).show()
////                    CloudFireStore().UpdateMyGame(mAuth!!.currentUser!!.uid, gameId, inputCode,"index")
////                    Log.d("errorSearch",mAuth!!.currentUser!!.uid)
//                    CloudFireStore().UpdateMyGame(ProfileFragment.currentUId, itemList[position].profGameId, itemList[position].profGameFriendCode, true,"index")
//                }
//                else{
//                    CloudFireStore().UpdateMyGame(ProfileFragment.currentUId, itemList[position].profGameId, itemList[position].profGameFriendCode, false,"index")
////                    CloudFireStore().UpdateMyGame(mAuth!!.currentUser!!.uid, itemList[position].profGameId, itemList[position].profGameFriendCode, true,"index")
//                }
//            }

            it.itemTextView.text = itemList[position].profGameFriendCode
            var copyText = it.itemTextView.text

            /**
             * コピーボタン押下時、フレンドコードをクリップボードへコピー
             * (追加でアプリ起動)
             */
            it.itemButton.setOnClickListener {
//                Log.d("push_copyButton",itemList[position].profGameId.toString())
                Toast.makeText(this.context, copyText.toString(), Toast.LENGTH_SHORT).show()
            }
//            it.itemImageView.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.search_recycler_item, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return SearchRecyclerViewHolder(mView)
    }
}