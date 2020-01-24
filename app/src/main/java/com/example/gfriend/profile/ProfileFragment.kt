package com.example.gfriend.profile

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gfriend.AddGameFragment
import com.example.gfriend.R
import com.example.gfriend.list.ProfileGameListRecyclerAdapter
import com.example.gfriend.list.ProfileRecyclerViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), ProfileRecyclerViewHolder.ItemClickListener {
    private var mAuth: FirebaseAuth? = null
    private var profMutableList = mutableListOf<ProfilePageInfo>()

    val db = FirebaseFirestore.getInstance()

    val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeFlag(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.RIGHT
            ) or makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            if (direction == ItemTouchHelper.LEFT) {
                val dialog = AlertDialog.Builder(context)
                dialog.setMessage("削除しますか？")
                dialog.setOnDismissListener {
                    val position = viewHolder.layoutPosition
                    viewHolder.let { vh ->
                        profile_recyclerView?.adapter?.let {
//                            it.notifyItemRemoved(vh.layoutPosition-1)
                            it.notifyItemRangeChanged(vh.layoutPosition, it.itemCount)
//                            it.notifyDataSetChanged()
                        }
                    }
                }
                dialog.setPositiveButton("はい") { _: DialogInterface, i: Int ->
                    val position = viewHolder.layoutPosition
                    viewHolder.let { vh ->
                        profile_recyclerView?.adapter?.let {
                            val recyclerViewAdapter = it
                            db.collection("index")
                                .whereEqualTo("uId", mAuth!!.currentUser?.uid.toString())
                                .whereEqualTo("gId", profMutableList[position].profGameId)
                                .get()
                                .addOnSuccessListener {

                                    for( document in it){
                                        Log.d("it", document.id.toString())
                                        db.collection("index").document(document.id)
                                            .delete()
                                            .addOnSuccessListener {
//                                                Log.d("remove","success!!")

                                                recyclerViewAdapter.notifyItemRemoved(position)
//                                                recyclerViewAdapter.notifyItemRangeChanged(position, recyclerViewAdapter.itemCount-1)
//                                                recyclerViewAdapter.notifyDataSetChanged()

//                                                recyclerViewAdapter.notifyDataSetChanged()
                                            }
                                            .addOnFailureListener {
//                                                recyclerViewAdapter.notifyDataSetChanged()
                                                Log.d("remove", it.toString())
                                            }
                                    }
//                                    recyclerViewAdapter.notifyItemRemoved(viewHolder.layoutPosition)
//                                    it.notifyItemRemoved(viewHolder.layoutPosition + 1)
//                                    recyclerViewAdapter.notifyItemRangeChanged(vh.layoutPosition, recyclerViewAdapter.itemCount)


                                }
                                .addOnFailureListener { e -> Log.d("aaaaa", e.toString()) }

                        }
                    }
                    return@setPositiveButton
                }.setNegativeButton("いいえ") { _: DialogInterface, i: Int ->
                    return@setNegativeButton
                }.show()
            }
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
        }


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }
    })
    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this.context, "position $position was tapped", Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        currentUId = mAuth!!.currentUser!!.uid

        /**
         * ゲームフィルターリストを取得・RecyclerViewの生成
         * */

        /**
         * 現在のユーザを取得
         * */
//        db.collection("users")
//            .whereEqualTo("user", mAuth!!.currentUser!!.email)
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("test", "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("test", "Error getting documents: ", exception)
//            }
//        arguments?.let {
//        }
        /**
         * 現在のユーザの登録済みゲームの一覧を表示
         * */
        db.collection("index")
            .whereEqualTo("uId", mAuth!!.currentUser!!.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
//                    Log.d("あああ", document.data["recFlag"].toString())
                    profMutableList.add(ProfilePageInfo(document.data["gId"] as Long, document.data["friendCode"].toString(), document.data["recFlag"] as Boolean, document.data["intro"].toString()))
                    Log.d("intro", document.data["intro"].toString())

                }
                profile_recyclerView.adapter = ProfileGameListRecyclerAdapter(this.context!!, this, profMutableList)
                profile_recyclerView.layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)
            }
            .addOnFailureListener { exception ->
            }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName.text = mAuth!!.currentUser!!.email
//        profile_recyclerView.adapter = ProfileGameListRecyclerAdapter(this.context!!, this, profMutableList)
        gameAddButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(
                R.id.main_content,
                AddGameFragment()
            )
                ?.commit()
        }
        itemTouchHelper.attachToRecyclerView(profile_recyclerView)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onDetach() {
        super.onDetach()
    }
    companion object {
        var currentUId:String = ""
    }
}
