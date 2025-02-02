package com.example.final_1.contentsList

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_1.R

class ContentRVAdapter(val context: Context, val items : ArrayList<ContentModel>) :

    RecyclerView.Adapter<ContentRVAdapter.Viewholder>() {

    interface ItemClick{
        fun onClick(view:View,position:Int)
    }

    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentRVAdapter.Viewholder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.content_rv_items,parent,false)
        return Viewholder(v)
    }

    override fun onBindViewHolder(holder: ContentRVAdapter.Viewholder, position: Int) {
        if(itemClick != null){
            holder.itemView.setOnClickListener{v ->
                itemClick?.onClick(v, position)
            }
        }
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView){


        fun bindItems(item : ContentModel){

            val contentTitle = itemView.findViewById<TextView>( R.id.text)
            val imageViewArea = itemView.findViewById<ImageView>(R.id.image)

            contentTitle.text = item.title

            Glide.with(context)
                .load(item.imageUrl)
                .into(imageViewArea)
        }
    }
}