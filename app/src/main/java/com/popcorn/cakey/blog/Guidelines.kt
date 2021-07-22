package com.popcorn.cakey.blog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R

class Guidelines(private val step : ArrayList<String>) : RecyclerView.Adapter<Guidelines.ViewHolder>()  {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Guidelines.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.guidelines, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: Guidelines.ViewHolder, position: Int) {
        holder.bindItems(step[position], position+1)
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(step:String, index : Int) {
            val Step = itemView.findViewById(R.id.step) as TextView

            val numberStep = "Step $index : $step"
            Step.text = numberStep
        }

    }

    override fun getItemCount(): Int {
        return step.size
    }
}

