package com.popcorn.cakey

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Fragment1Activity: Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerApdater.ViewHolder>? = null
    companion object{
        fun newInstance():Fragment1Activity{
            return Fragment1Activity()
        }
    }
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=RecyclerApdater()

        val view: View=inflater.inflate(R.layout.activity_suggestion,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.recyclerView.apply{
            layoutManager=LinearLayoutManager(activity)
            adapter=RecyclerApdater()
        }
    }
}
class RecyclerApdater: RecyclerView.Adapter<RecyclerApdater.ViewHolder>(){

    private var title= arrayOf("ID1", "ID2","ID3", "ID4")
    private var image= intArrayOf(R.drawable.avatar,R.drawable.avatar,R.drawable.avatar,R.drawable.avatar)

    fun RecyclerApdater(){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerApdater.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_suggestion,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerApdater.ViewHolder, position: Int) {
        holder.itemTitle.text=title[position]
        holder.itemImage.setImageResource(image[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView

        init{
            itemImage=itemView.findViewById(R.id.thumnail)
            itemTitle=itemView.findViewById(R.id.username)
        }
    }
}
