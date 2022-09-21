package com.shopingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.shopingapp.R
import com.shopingapp.dao.interfaceCallback.ItemClickListener
import com.shopingapp.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_cart_product.view.*

class ApdapterItemCart(private var listProduct:MutableList<Product>, val context:Context, var itemClickListener: ItemClickListener):RecyclerView.Adapter<ApdapterItemCart.ItemCartViewHolder>() {
    var db = FirebaseStorage.getInstance()
    class ItemCartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var imgProduct = itemView.img_itemcart
        var imgDelete = itemView.img_delete_itemcart
        var txtProductName = itemView.txt_product_name_itemcart
        var txtProductPrice = itemView.txt_product_price_itemcart
        var checkBox = itemView.checkbox_itemcart
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        return  ItemCartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product,parent,false))
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        var product = listProduct[position]
        holder.txtProductName.text = product.productName
        holder.txtProductPrice.text = product.price.toString()
        var imgRef = db.reference.child("images").child("products_image").child(product.imgURL)
        imgRef.downloadUrl.addOnSuccessListener {
            Picasso.with(context).load(it).into(holder.imgProduct)
        }
        // handler click listener
        holder.imgDelete.setOnClickListener {
            itemClickListener.onItemDeleteClick(product,position)
        }
        holder.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                itemClickListener.onItemCheckClick(product,p1)
            }

        })

    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}