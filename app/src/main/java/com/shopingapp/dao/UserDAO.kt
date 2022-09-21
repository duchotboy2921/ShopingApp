package com.shopingapp.dao

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.shopingapp.dao.interfaceCallback.SuccessCallback
import com.shopingapp.dao.interfaceCallback.UserCallback
import com.shopingapp.model.Product
import com.shopingapp.model.User

class UserDAO {
    private var uid = FirebaseAuth.getInstance().uid
    private var dp = Firebase.firestore
    private var userRef = dp.collection("Users").document(uid.toString())

    fun getCurrentUserInfo(userCallback: UserCallback){
        userRef.get()
            .addOnSuccessListener {
                var user = it.toObject(User::class.java)
                Log.d("testuser",it.data.toString())
                if (user != null) {
                    Log.d("testuser",user.username)
                    userCallback.getCallback(user)
                }
            }
    }
    fun addUserToDocument(id:String,user:User){
        val user1 = hashMapOf(
            "username" to user.username,
            "password" to user.password,
            "productsInCart" to mutableListOf<Product>(),
            "purchasedProducts" to mutableListOf<Product>()
        )
        dp.collection("Users")
            .document(id)
            .set(user1)
            .addOnCompleteListener {
                Log.d("addUser","Add user success: "+ user.username)
            }
            .addOnFailureListener{
                Log.d("addUser","Add user fail: "+it.message)
            }
    }
}