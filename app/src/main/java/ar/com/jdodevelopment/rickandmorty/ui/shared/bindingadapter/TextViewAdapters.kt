package ar.com.jdodevelopment.rickandmorty.ui.shared.bindingadapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ar.com.jdodevelopment.rickandmorty.R
import ar.com.jdodevelopment.rickandmorty.data.consts.CharacterConsts

object TextViewAdapters {


    @JvmStatic
    @BindingAdapter("statusIcon")
    fun bindStatusIcon(textView: TextView, status: String) {
        val context = textView.context
        val drawable = when (status) {
            CharacterConsts.Status.ALIVE -> ContextCompat.getDrawable(context, R.drawable.ic_status_alive)
            CharacterConsts.Status.DEAD -> ContextCompat.getDrawable(context, R.drawable.ic_status_dead)
            CharacterConsts.Status.UNKNOWN -> ContextCompat.getDrawable(context, R.drawable.ic_status_unknow)
            else -> null
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }

}