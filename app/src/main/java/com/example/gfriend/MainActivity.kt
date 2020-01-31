package com.example.gfriend

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.FirebaseFirestore
import com.example.gfriend.profile.ProfileFragment







class MainActivity : AppCompatActivity() {
    var gamenumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.ThemeOverlay_MaterialComponents_Dark)
        val db = FirebaseFirestore.getInstance()
        val manifestFont = Typeface.createFromAsset(assets, "MANIFESTO.ttf")

/**
 * 画像をメモリ内に保存
 * */
//
//        db.collection("games")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    gamenumber += 1
////                            Log.d("ユーザの登録済みゲーム一覧", "${document.id} => ${document.data["friendCode"]}")
//                }
//                val storage: FirebaseStorage = FirebaseStorage.getInstance()
//                val storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
//                for (i in 1 ..gamenumber){
//                    val pathReference = storageRef.child("gameIcon/${i}.png")
//                    /**
//                     * メモリに保存する
//                     * */
//                    val ONE_MEGABYTE = (1024 * 1024).toLong()
//                    pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(OnSuccessListener<ByteArray> {
//                        val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
//                        saveImage(bitmap, "${i}.png", this)
////            readImage("1.png",this)
//                        // Data for "images/island.jpg" is returns, use this as needed
//                    }).addOnFailureListener(OnFailureListener {
//                        // Handle any errors
//                        Log.d("SuccessListener",it.toString())
//                    })
//                }
//            }
//            .addOnFailureListener { exception ->
//                //                        Log.d("ユーザの登録済みゲーム一覧", exception.toString())
//            }


//        pathReference.downloadUrl.addOnSuccessListener{
//
////            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
////            var bitmap = uriToBitmap(it)
////            if(bitmap != null) {
////                saveImage(bitmap,"1.png", this)
////            }else{
////                Log.d("どんまい",it.toString())
////            }
//        }



//        /**
//         * ファイルに保存する
//         *　*/
//
//        val tmpDir = File("drawable://")
//        val localFile = File.createTempFile("images", "png", tmpDir)
//
//        pathReference.getFile(localFile)
//            .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot> {
//                // Local temp file has been created
//                Log.d("a","Success")
//            }).addOnFailureListener(OnFailureListener {
//                // Handle any errors
//                Log.d("a","Failure")
//            })




        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, ProfileFragment())
            .commit()


        bottomNavi.setOnNavigationItemSelectedListener { item ->
            // 各選択したときの処理
            when (item.itemId) {
                R.id.menu_setting -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, SettingFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, SearchFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, ProfileFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


    }

//    //uriからBitmapに変換（失敗した場合nullを返す）
//    fun uriToBitmap(uri: Uri): Bitmap?{
//        var pfDescriptor: ParcelFileDescriptor? = null
//        var bitmap: Bitmap? = null
//        try{
//            pfDescriptor = contentResolver.openFileDescriptor(uri,"r")
//            if(pfDescriptor != null) {
//                val fileDescriptor: FileDescriptor = pfDescriptor.fileDescriptor
//                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
//                pfDescriptor.close()
//            }
//
//        }catch (e:Exception){
//            e.printStackTrace();
//        }finally {
//            try{
//                if(pfDescriptor != null){
//                    pfDescriptor.close()
//                }
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//        return bitmap
//    }
//    //Stringからもいけるように追加
//    fun uriToBitmap(uri: String): Bitmap?{
//        var pfDescriptor: ParcelFileDescriptor? = null
//        var bitmap: Bitmap? = null
//        try{
//            val uri = uri.toUri()
//            pfDescriptor = contentResolver.openFileDescriptor(uri,"r")
//            if(pfDescriptor != null) {
//                val fileDescriptor: FileDescriptor = pfDescriptor.fileDescriptor
//                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
//                pfDescriptor.close()
//            }
//
//        }catch (e:Exception){
//            e.printStackTrace();
//        }finally {
//            try{
//                if(pfDescriptor != null){
//                    pfDescriptor.close()
//                }
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//        return bitmap
//    }

}


