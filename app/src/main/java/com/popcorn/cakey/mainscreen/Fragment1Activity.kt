package com.popcorn.cakey.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R


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

        val view: View=inflater.inflate(R.layout.activity_fragment1,container,false)
        layoutManager= LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView=view.findViewById(R.id.suggestionView)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=RecyclerApdater()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        R.id.suggestionView.apply{
            layoutManager=LinearLayoutManager(activity)
            adapter=RecyclerApdater()
        }
    }
}
class RecyclerApdater: RecyclerView.Adapter<RecyclerApdater.ViewHolder>(){

    private var title= arrayOf("anh1", "anh2","anh3", "anh4","anh5", "anh6","anh7", "anh8")
    private var image= intArrayOf(R.drawable.avatar,R.drawable.avatar,R.drawable.avatar,
        R.drawable.avatar,R.drawable.avatar,R.drawable.avatar,R.drawable.avatar,R.drawable.avatar)

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
        var itemImage: ImageView = itemView.findViewById(R.id.thumnail)
        var itemTitle: TextView = itemView.findViewById(R.id.username)

    }
}
