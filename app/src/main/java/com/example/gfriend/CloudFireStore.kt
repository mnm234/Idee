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
     * Myゲームの新規登録
     * */
    fun InsertMyGame(userdata: HashMap<String, Any>, table:String){
        mAuth = FirebaseAuth.getInstance()
        db.collection(table)
            .add(userdata)
            .addOnSuccessListener { documentReference ->
                Log.d("addGame", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("addGame", "Error adding document", e)
            }
    }

    /**
     * MyゲームフレンドコードUpdate
     * */

    fun UpdateMyGame(uId:String, gId:Long, friendCode:String, Recruitment:Boolean, table:String){
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
                                    "recFlag" to Recruitment
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
                                "recFlag" to Recruitment
                            )
                        )
                        .addOnSuccessListener { documentReference ->
                        }
                        .addOnFailureListener { e ->
                        }
                }
            }


    }

    /**
     * ゲーム全件検索
     * */

    fun GetGameListAll(){

        db.collection("games")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                }
            }
            .addOnFailureListener { exception ->
            }
    }


    fun GetMygameList(user:String){
        db.collection("index")
            .whereEqualTo("user", user)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    Log.d("tag", "gameId:" + document["gameId"].toString())
                }
            }
    }

    /**
     * Firebase上の画像をImageViewに表示
     * */
//    fun TestStorage(){
//        var storage: FirebaseStorage = FirebaseStorage.getInstance()
//        var storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
//        var pathReference = storageRef.child("gameIcon/Fortnite.jpg")
//        Log.d("imim",pathReference.toString())
//        pathReference.downloadUrl.addOnSuccessListener {
//            Log.d("imim", it.toString())
//            Glide.with(this.context!!)
//                .load(it)
//                .into(ImageView)
//        }

//    }
}


/**
 *
 * */