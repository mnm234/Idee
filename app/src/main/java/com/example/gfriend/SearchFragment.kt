package com.example.gfriend

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gfriend.list.*
import com.example.gfriend.profile.ProfilePageInfo
import com.example.gfriend.profile.SearchGameFilter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_search.*
import java.io.IOException

class SearchFragment : Fragment(), SearchGameFilterRecyclerViewHolder.ItemClickListener,
    SearchRecyclerViewHolder.ItemClickListener {

    private var mAuth: FirebaseAuth? = null
    private var searchMutableList = mutableListOf<ProfilePageInfo>()
    private var filterMutableList = mutableListOf<SearchGameFilter>()
    private var selectItemPosition:Int ?= 99999

    override fun onItemClick(view: View, position: Int) {
        Log.d("view", view.toString())
        Log.d("position", position.toString())
    }

    override fun filterOnItemClick(view: View, position: Int) {
        super.filterOnItemClick(view, position)
        if(position!=selectItemPosition){
            selectItemPosition = position
            searchMutableList.clear()
            val db = FirebaseFirestore.getInstance()
            db.collection("index")
                .whereEqualTo("gId", filterMutableList[position].FilterGameId)
                .whereEqualTo("recFlag", true)
                .get()
                .addOnSuccessListener {
                    for( document in it){
                        searchMutableList.add(ProfilePageInfo(document.data["gId"] as Long, document.data["friendCode"].toString(),document.data["recFlag"] as Boolean, document.data["intro"].toString()))
                    }
//                Log.d("s",searchMutableList.toString())
                    search_recyclerview.adapter = SearchGameListRecyclerAdapter(this.context!!, this, searchMutableList)
                }
        }
//        Log.d("a",filterMutableList[position].FilterGameId.toString())

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        mAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        db.collection("index")
            .whereEqualTo("uId", mAuth!!.currentUser!!.uid)
            .get()
            .addOnSuccessListener {result ->
                for( document in result){
                    filterMutableList.add(SearchGameFilter(document.data["gId"] as Long))
                }
                search_game_filter.adapter = SearchGameFilterRecyclerAdapter(this.context!!, this, filterMutableList)
                search_game_filter.layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.HORIZONTAL,false)
            }


        db.collection("index")
            .whereEqualTo("recFlag",true)
            .get()
            .addOnSuccessListener {result ->
                for( document in result){
                    searchMutableList.add(ProfilePageInfo(document.data["gId"] as Long, document.data["friendCode"].toString(), document.data["recFlag"] as Boolean, document.data["intro"].toString()))
                }
                search_recyclerview.adapter = SearchGameListRecyclerAdapter(this.context!!, this, searchMutableList)
                search_recyclerview.layoutManager = LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)

            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    fun copy(copyText:String){
        try {
            mManager.setPrimaryClip(ClipData.newPlainText("label", copyText))
        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }
    companion object{
        lateinit var mManager:ClipboardManager
    }



}
