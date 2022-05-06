package com.example.recipepool.recycleradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipepool.R
import com.example.recipepool.data.SearchList

class RecyclerAdapterSearchList(val data: ArrayList<SearchList>) :
    RecyclerView.Adapter<RecyclerAdapterSearchList.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_search_recipe,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val input = data[position]
        holder.bind(input)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.findViewById<TextView>(R.id.search_rv_text)
        val image = v.findViewById<ImageView>(R.id.search_rv_image)
        fun bind(data: SearchList){
            name.text = data.name.toString()
            Glide.with(itemView.context)
                .load(data.image.toString())
                .into(image)
        }
    }
}