package com.shopingapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.shopingapp.activity.ProductDetailActivity
import com.shopingapp.R
import com.shopingapp.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterProduct(var list:ArrayList<Product>,var context:Context): RecyclerView.Adapter<AdapterProduct.ViewHolder>() {
    var db = FirebaseStorage.getInstance()
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var imgProduct = itemView.img_item_product
        var txtProductName = itemView.txt_item_product_name
        var txtProductPrice = itemView.txt_item_product_price
        var ratingBar = itemView.ratingbar_item_product
        var txtCity = itemView.txt_item_product_loc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = list.get(position)
        var imgRef = db.reference.child("images").child("products_image").child(product.imgURL)
        holder.txtProductName.text = product.productName
        holder.txtProductPrice.text = product.price.toString()
        holder.ratingBar.rating = product.ratingStar.toFloat()
        holder.txtCity.text = product.productCity
        imgRef.downloadUrl.addOnSuccessListener {
            try {
                Picasso.with(context).load(it).into(holder.imgProduct)      //load image from url
            }catch (ex:Exception){
                Log.e("picasso","error")
            }
        }

        holder.itemView.setOnClickListener {
            var intent =Intent(context, ProductDetailActivity::class.java)             //click on product to open ProductDetail page
            intent.putExtra("product",product)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}