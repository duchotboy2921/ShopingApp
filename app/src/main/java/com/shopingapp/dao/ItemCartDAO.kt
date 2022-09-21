package com.shopingapp.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shopingapp.dao.interfaceCallback.SuccessCallback
import com.shopingapp.model.Product

class ItemCartDAO {
    private var uid = FirebaseAuth.getInstance().uid
    private var db = Firebase.firestore
    private var cartRef = db.collection("Users").document(uid.toString())
    fun addProductToCart(product:Product,successCallback: SuccessCallback){
        cartRef
            .update("productsInCart",FieldValue.arrayUnion(product))
            .addOnSuccessListener {
                Log.d("test","Add product to cart successfully")
                successCallback.getCallback(true)
            }
            .addOnFailureListener {
                Log.d("test","Add product to cart failed")
                successCallback.getCallback(false)
            }
    }
    fun deleteProductFromCart(product: Product,successCallback: SuccessCallback){
        cartRef
            .update("productsInCart",FieldValue.arrayRemove(product))
            .addOnSuccessListener {
                Log.d("deleteProduct","delete ${product.productName} successfully")
                successCallback.getCallback(true)
            }
            .addOnFailureListener {
                Log.d("deleteProduct","delete ${product.productName} failed")
                successCallback.getCallback(false)
            }
    }
    fun deleteListProductFromCart(products:Array<Product>,successCallback: SuccessCallback){
        cartRef
            .update("productsInCart",FieldValue.arrayRemove(*products))
            .addOnSuccessListener {
                Log.d("deleteProduct","delete  successfully")
                successCallback.getCallback(true)
            }
            .addOnFailureListener {
                Log.d("deleteProduct","delete failed")
                successCallback.getCallback(false)
            }
    }
    fun addProductToPurchasedList(products:Array<Product> ){
        cartRef
            .update("purchasedProducts",FieldValue.arrayUnion(*products))
            .addOnSuccessListener {
                Log.d("addPurchasedProducts","Success")
            }
            .addOnFailureListener{
                Log.d("addPurchasedProducts","Failed")
            }
    }
}