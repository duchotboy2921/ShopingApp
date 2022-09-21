package com.shopingapp.dao

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shopingapp.dao.interfaceCallback.ProductCallback
import com.shopingapp.dao.interfaceCallback.SuccessCallback
import com.shopingapp.model.Product

class ProductDAO {
    private var db = Firebase.firestore.collection("Products")
    private var uid = FirebaseAuth.getInstance().uid
    fun getNewProduct(productCallback: ProductCallback){
        var list = ArrayList<Product>()
        db.limit(10)                                    // get 10 latest product
            .whereNotEqualTo("idShop",uid)
            .get()
            .addOnSuccessListener {
                for (document in it){
                    var product = document.toObject(Product::class.java)
                    list.add(product)
                }
                productCallback.getCallBack(list)
            }
    }
    fun getProductByType(type:String,productCallback: ProductCallback){
        var list = ArrayList<Product>()
        db.whereEqualTo("type",type)            // get all products which have tye = "type"
            .get()
            .addOnSuccessListener {
                for(document in it){
                    var product = document.toObject(Product::class.java)
                    Log.d("testCallBack",product.productName)
                    list.add(product)
                }
                productCallback.getCallBack(list)
            }
    }
    fun getOnSaleProduct(productCallback: ProductCallback){
        var list = ArrayList<Product>()
        db.whereEqualTo("idShop",uid)           // get all product which have idShop equals to id of current user
            .get()
            .addOnSuccessListener {
                for(document in it){
                   var product = document.toObject(Product::class.java)
                    Log.d("getOnSaleProduct",product.productName)
                   list.add(product)
                }
                productCallback.getCallBack(list)
            }
    }
    fun addProduct(product: Product, successCallback: SuccessCallback){
        var product1 = hashMapOf(
            "productName" to product.productName,
            "quantity" to product.quantity,
            "price" to product.price,
            "productCity" to product.productCity,
            "imgURL" to product.imgURL,
            "idShop" to product.idShop,
            "type" to product.type,
            "ratingStar" to 0
        )
        db.document(product.imgURL)
            .set(product1)
            .addOnSuccessListener {
                Log.d("add_product","ok")
                successCallback.getCallback(true)
            }
            .addOnFailureListener{
                Log.d("add_product","fail")
                successCallback.getCallback(false)
            }
    }
    fun getProductByName(productName:String ,productCallback: ProductCallback){
        var list = ArrayList<Product>()
        db
            .orderBy("productName")
            .startAt(productName)
            .endAt(productName+"\uf8ff")
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    var product = document.toObject(Product::class.java)
                    Log.d("searchProduct", product.productName)
                    list.add(product)
                }
                productCallback.getCallBack(list)
            }
    }

}