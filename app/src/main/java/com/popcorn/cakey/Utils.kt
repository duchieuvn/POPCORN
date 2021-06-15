package com.popcorn.cakey

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
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

    @JvmStatic
    fun blockInput(progressBar: ProgressBar, vararg toBeDisable: TextView) {
        progressBar.visibility = View.VISIBLE
        for (elem in toBeDisable)
            elem.isEnabled = false
    }

    @JvmStatic
    fun unblockInput(progressBar: ProgressBar, vararg toBeDisable: TextView) {
        progressBar.visibility = View.GONE
        for (elem in toBeDisable)
            elem.isEnabled = true
    }
}