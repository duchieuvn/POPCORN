package com.popcorn.cakey

import android.content.Context
import android.widget.Toast

object Utils {
    @JvmStatic
    fun showToast(context: Context, resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, resId, duration).show()
    }

    @JvmStatic
    fun showToast(context: Context, text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, duration).show()
    }
}