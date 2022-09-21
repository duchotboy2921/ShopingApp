package com.shopingapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shopingapp.activity.ProductDetailActivity
import com.shopingapp.R

class AdapterTest(val list:List<String>,val context:Context):RecyclerView.Adapter<AdapterTest.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, ProductDetailActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}