package com.example.gfriend

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_add_dialog.*


class AddDialogFragment : DialogFragment() {

    private var gId:Long = 0
    private lateinit var pathReference:StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = FirebaseFirestore.getInstance()
        db.collection("games")
            .whereEqualTo("gameId", AddGameFragment.gameId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    gId = document.data["gameId"] as Long
                }
                val storage: FirebaseStorage = FirebaseStorage.getInstance()
                val storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
                pathReference = storageRef.child("gameIcon/${gId}.png")
                pathReference.downloadUrl.addOnSuccessListener {
                    Glide.with(this.context!!)
                        .load(it)
                        .into(kakunin_Icon)
                }
                kakunin_friendcode.text = AddGameFragment.fcode

                update_button.setOnClickListener {
                    AddGameFragment().DialogOK(AddGameFragment.fcode,gId)
                    dialog!!.dismiss()
                }
                cancel_button.setOnClickListener {
                    dialog!!.dismiss()
                }
            }



    }

    companion object
}
