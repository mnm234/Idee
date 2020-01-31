package com.example.gfriend

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage


class SpinnerAdapter internal constructor(
    private val context: Context,
    private val layoutID: Int,
    private val names: Array<String>,
    private val spinnerImages: Array<String>
) : BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val imageIDs: IntArray = IntArray(spinnerImages.size)

    internal class ViewHolder {
        var imageView: ImageView? = null
        var textView: TextView? = null
    }

    init {

        val res = context.resources

        // 最初に画像IDを配列で取っておく
        for (i in spinnerImages.indices) {
            imageIDs[i] = res.getIdentifier(
                spinnerImages[i],
                "drawable", context.packageName
            )
        }
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val holder: ViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null)
            holder = ViewHolder()

            holder.imageView = convertView!!.findViewById(R.id.spinner_image_view)
            holder.textView = convertView.findViewById(R.id.spinner_text_view)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

////        holder.imageView!!.setImageResource(imageIDs[position])
//        var storage: FirebaseStorage = FirebaseStorage.getInstance()
//        var storageRef = storage.getReferenceFromUrl("gs://gfriend-65dbd.appspot.com")
//        var pathReference = storageRef.child("gameIcon/${spinnerImages[position]}.png")
//        var a = it
//        pathReference.downloadUrl.addOnSuccessListener {
//            Glide.with(this.context)
//                .load(it)
//                .apply(
//                    RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .placeholder(R.drawable.ic_image_black_24dp)
//                        .fitCenter())
//                .into(holder.imageView!!)
//
//        }
        holder.imageView!!.setImageBitmap(readImage("${spinnerImages[position]}.png",context))
        holder.textView!!.text = names[position]


        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("position",position.toString())
        return super.getDropDownView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return names.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}