package ar.com.jdodevelopment.rickandmorty.ui.shared.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


object ImageViewAdapters {


    /**
     * Commons
     */
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(imageView: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl", "placeholder")
    fun imageUrlWithPlaceholder(imageView: ImageView, imageUrl: String?, placeholder: Drawable) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        } else {
            imageView.setImageDrawable(placeholder)
        }
    }

}