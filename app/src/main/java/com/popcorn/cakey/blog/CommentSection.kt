package com.popcorn.cakey.blog

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R
import java.io.File

class CommentSection(private val user : ArrayList<String>, private val cmt : ArrayList<String>,
                     private val ava : ArrayList<File>) : RecyclerView.Adapter<CommentSection.ViewHolder>()  {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentSection.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comment_section, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CommentSection.ViewHolder, position: Int) {
        holder.bindItems(user[position],cmt[position], ava[position])
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(user:String, cmt:String, ava:File) {
            val userName = itemView.findViewById(R.id.userName) as TextView
            userName.text = user

            val userCMT = itemView.findViewById(R.id.userCmt) as TextView
            userCMT.text = cmt

            val userAvatar = itemView.findViewById(R.id.userAvatar) as de.hdodenhof.circleimageview.CircleImageView

            val myBitmap = BitmapFactory.decodeFile(ava.absolutePath)
            userAvatar.setImageBitmap(myBitmap)

        }

    }

    override fun getItemCount(): Int {
        return user.size
    }
}

