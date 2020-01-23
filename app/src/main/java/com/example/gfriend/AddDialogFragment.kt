package com.example.gfriend

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_add_dialog.*
import java.lang.Exception


class AddDialogFragment : DialogFragment() {

    private var gId: Long = 0
    private lateinit var pathReference: StorageReference

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
//                val storage: FirebaseStorage = FirebaseStorage.getInstance()
//                val storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
//                pathReference = storageRef.child("gameIcon/${gId}.png")
//                pathReference.downloadUrl.addOnSuccessListener {
//                    Glide.with(this.context!!)
//                        .load(it)
//                        .into(kakunin_Icon)
//                }
                kakunin_Icon.setImageBitmap(
                    (readImage(
                        "${gId}.png",
                        this.context!!
                    ))
                )
                introduction_textView.text = AddGameFragment.iText
                kakunin_friendcode.text = AddGameFragment.fcode

                update_button.setOnClickListener {
                    try {

                        if (introduction_textView.text.isEmpty()) {
                            Toast.makeText(context, "'紹介文'が未入力です。入力してください。", Toast.LENGTH_SHORT)
                                .show()
                        } else if (kakunin_friendcode.text.isEmpty()) {
                            Toast.makeText(context, "'フレンドコード'が未入力です。入力してください。", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            AddGameFragment().DialogOK(
                                AddGameFragment.fcode,
                                gId,
                                AddGameFragment.iText
                            )
                        }

                    } catch (e: Exception) {

                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                    dialog!!.dismiss()
                }
                cancel_button.setOnClickListener {
                    dialog!!.dismiss()
                }
            }


    }

}
