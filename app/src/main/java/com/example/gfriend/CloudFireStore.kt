package com.example.gfriend

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference


class CloudFireStore {
    private var mAuth: FirebaseAuth? = null
    private var mStorageRef: StorageReference? = null
    val db = FirebaseFirestore.getInstance()
    private var counter = 0

    /**
     * MyゲームフレンドコードUpdate
     * */

    fun UpdateMyGame(uId:String, gId:Long, friendCode:String, intro:String,Recruitment:Boolean, table:String){
        mAuth = FirebaseAuth.getInstance()
        db.collection(table)
            .whereEqualTo("uId",uId)
            .whereEqualTo("gId",gId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("fafa",document.data.toString())
                    counter += 1
                        db.collection(table).document(document.id)
                            .set(
                                hashMapOf(
                                    "uId" to uId,
                                    "gId" to gId,
                                    "friendCode" to friendCode,
                                    "recFlag" to Recruitment,
                                    "intro" to intro
                                )
                            )
                }
                if(counter == 0){
                    db.collection(table)
                        .add(
                            hashMapOf(
                                "uId" to uId,
                                "gId" to gId,
                                "friendCode" to friendCode,
                                "recFlag" to Recruitment,
                                "intro" to intro
                            )
                        )
                        .addOnSuccessListener { documentReference ->
                        }
                        .addOnFailureListener { e ->
                        }
                }
            }


    }





}