package com.example.movietmdb

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class GlideBindingAdapter {

    companion object{
        @BindingAdapter("downloadImage")
        fun downlaodImage(view: ImageView, imageUrl: String) {
            val request = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(view.context).load(imageUrl).apply(request).into(view)
        }
    }

}