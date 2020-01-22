package com.example.gfriend

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import java.io.*

//画像保存
public fun saveImage(bmp: Bitmap, outputFileName:String, context: Context){
    try {
        val byteArrOutputStream = ByteArrayOutputStream()
        val fileOutputStream: FileOutputStream = context.openFileOutput(outputFileName, Context.MODE_PRIVATE)
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrOutputStream)
        fileOutputStream.write(byteArrOutputStream.toByteArray())
        fileOutputStream.close()
    }
    catch (e: Exception){
        e.printStackTrace()
    }
}

//画像読込
public fun readImage(fileName:String, context: Context): Bitmap? {
    try {
        val bufferedInputStream = BufferedInputStream(context.openFileInput(fileName))
        return BitmapFactory.decodeStream(bufferedInputStream)
    }
    catch (e: IOException){
        e.printStackTrace()
        return null
    }
}
