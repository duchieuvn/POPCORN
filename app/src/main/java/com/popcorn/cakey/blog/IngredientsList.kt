package com.popcorn.cakey.blog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.popcorn.cakey.R

class IngredientsList(private val quantity:ArrayList<Int>, private val unit : ArrayList<String>, private val name : ArrayList<String> ) : RecyclerView.Adapter<IngredientsList.ViewHolder>()  {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsList.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ingredients_list, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: IngredientsList.ViewHolder, position: Int) {
        holder.bindItems(quantity[position], unit[position], name[position])
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(quantity:Int, unit:String, name:String) {
            val Quantity = itemView.findViewById(R.id.quantity) as TextView
            val Units  = itemView.findViewById(R.id.unit) as TextView
            val nameIngredient = itemView.findViewById(R.id.nameIngredient) as TextView

            Quantity.text = quantity.toString()
            Units.text = unit
            nameIngredient.text = name
        }

    }

    override fun getItemCount(): Int {
        return quantity.size
    }
}

