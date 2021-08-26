package com.popcorn.cakey.mainscreen

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.parse.ParseFile

@SuppressLint("ParcelCreator")
data class BlogThumbnails(var ID:String?, var title: String?, var image: ParseFile, var author: String?)

