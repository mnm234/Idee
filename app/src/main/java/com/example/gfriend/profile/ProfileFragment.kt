package com.example.gfriend.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
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
