package com.example.gfriend

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_game.*
import android.widget.AdapterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.spinner.*




class AddGameFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null
    private var gId:Long = 0
    var user = ""


    private var spinnerItems = arrayListOf<String>()
    private var spinnerImages = arrayListOf<String>()

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        user = mAuth!!.currentUser!!.uid

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        db.collection("games")
                    .orderBy("gameId")
                    .get()
                    .addOnSuccessListener {result ->
                        for(document in result) {
                            spinnerItems.add(document.data["gameName"].toString())
                            spinnerImages.add(document.data["gameId"].toString())
//                            Log.d("ユーザの登録済みゲーム一覧", "${document.id} => ${document.data["friendCode"]}")
                        }
                        Log.d("s",spinnerItems.toString())
                        val adapter = SpinnerAdapter(
                            this.context!!,
                            R.layout.spinner, spinnerItems.toTypedArray(), spinnerImages.toTypedArray()
                        )
                        my_game_select.adapter = adapter

                        my_game_select.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            //　アイテムが選択された時
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                        viw: View, position: Int, id: Long
                    ) {
//                        spinner_image_view.setImageResource(
//                            resources.getIdentifier(
//                                spinnerImages[position],
//                                "drawable", "png"
//                            )
//                        )
                        db.collection("games")
                            .whereEqualTo("gameName", spinnerItems[position])
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    gId = document.data["gameId"] as Long
//                            Log.d("ユーザの登録済みゲーム一覧", "${document.id} => ${document.data["friendCode"]}")
                                }
                            }
                            .addOnFailureListener { exception ->
                                //                        Log.d("ユーザの登録済みゲーム一覧", exception.toString())
                            }


                    }

                    //　アイテムが選択されなかった
                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }
        val adapter = SpinnerAdapter(
            this.context!!,
            R.layout.spinner, spinnerItems.toTypedArray(), spinnerImages.toTypedArray()
        )
        my_game_select.adapter = adapter
//        my_game_select.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            //　アイテムが選択された時
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                viw: View, position: Int, id: Long
//            ) {
//                spinner_image_view.setImageResource(
//                    resources.getIdentifier(
//                        spinnerImages[position],
//                        "drawable", "png"
//                    )
//                )
//                db.collection("games")
//                    .whereEqualTo("gameName", spinnerItems[position])
//                    .get()
//                    .addOnSuccessListener { result ->
//                        for (document in result) {
//                            gId = document.data["gameId"] as Long
////                            Log.d("ユーザの登録済みゲーム一覧", "${document.id} => ${document.data["friendCode"]}")
//                        }
//                    }
//                    .addOnFailureListener { exception ->
////                        Log.d("ユーザの登録済みゲーム一覧", exception.toString())
//                    }
//
//
//            }
//
//            //　アイテムが選択されなかった
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        }

        mygame_submit_button.setOnClickListener {


//            AlertDialog.Builder(this.context)
//                .setTitle("ダイアログのタイトルです")
//                .setMessage("ダイアログのメッセージです。伝えたい内容をここに記入してください。")
//                .setPositiveButton("更新"){ dialog, which ->  }
//                .show()


            val dialogFragment = AddDialogFragment()
            fcode = gameIdRegistration.text.toString()
            gameId = gId
            userId = mAuth!!.currentUser!!.uid
            iText = IntroductionAboutMe.text.toString()
            dialogFragment.show(this.fragmentManager!!, "test alert dialog")

            /**
             * データベースにマイゲーム追加、登録
             * */

//            val userdata = hashMapOf(
//                "uId" to user.toString(),
//                "gId" to gId,
//                "friendCode" to gameIdRegistration.text.toString()
//            )

//            CloudFireStore().InsertMyGame(userdata, "index")

        }
    }

    fun DialogOK(inputCode:String,gameId:Long,intro:String){
//        Log.d("popo", userId)
        CloudFireStore().UpdateMyGame(userId, gameId, inputCode, intro,false,"index")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        var fcode: String = ""
        var gameId: Long = 0
        var userId:String = ""
        var iText:String = ""

    }
}
