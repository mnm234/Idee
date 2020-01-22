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

class ProfileGameListRecyclerAdapter(
    private val context: Context,
    private val itemClickListener: ProfileRecyclerViewHolder.ItemClickListener,
    private val itemList: MutableList<ProfilePageInfo>
) : RecyclerView.Adapter<ProfileRecyclerViewHolder>() {

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

    override fun onBindViewHolder(holder: ProfileRecyclerViewHolder, position: Int) {
        holder.let {
//            val storage: FirebaseStorage = FirebaseStorage.getInstance()
//            val storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
//            val pathReference = storageRef.child("gameIcon/${itemList[position].profGameId}.png")
//            val a = it
//            pathReference.downloadUrl.addOnSuccessListener {
//                Glide.with(this.context)
//                    .load(it)
//                    .into(a.itemImageView)
//            }
            it.itemImageView.setImageBitmap(readImage("${itemList[position].profGameId}.png",context))
            it.itemSwitch.isChecked = itemList[position].recFlag


            it.itemSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    CloudFireStore().UpdateMyGame(ProfileFragment.currentUId, itemList[position].profGameId, itemList[position].profGameFriendCode, true,"index")
                }
                else{
                    CloudFireStore().UpdateMyGame(ProfileFragment.currentUId, itemList[position].profGameId, itemList[position].profGameFriendCode, false,"index")
                }
            }

            it.itemTextView.text = itemList[position].profGameFriendCode
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_item, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return ProfileRecyclerViewHolder(mView)
    }
}