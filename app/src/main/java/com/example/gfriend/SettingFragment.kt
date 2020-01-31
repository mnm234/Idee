package com.example.gfriend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class SettingFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//      val pathReference = storageRef.child("gameIcon/${itemList[position].profGameId}.png")
//        val a = it
//        pathReference.downloadUrl.addOnSuccessListener {
//            Glide.with(this.context)
//                .load(it)
//                .into(a.itemImageView)
//        }



    }


    companion object
}
