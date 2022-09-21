package com.shopingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shopingapp.R
import com.shopingapp.dao.UserDAO
import com.shopingapp.dao.interfaceCallback.SuccessCallback
import com.shopingapp.model.Product
import com.shopingapp.model.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Runnable

class RegisterActivity : AppCompatActivity() {
    private var userDAO = UserDAO()
    val auth:FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        retunToLoginPage()
        registerAccount()
    }

    private fun registerAccount() {
        btn_register.setOnClickListener {
            var userName = edt_username_register.text.toString().trim()
            var password = edt_password_register.text.toString()
            var rePassword = edt_password_register_again.text.toString()
            var user = User(userName,password, mutableListOf(), mutableListOf())
            if(userName.equals("")||password.equals("")||rePassword!=password){
                Toast.makeText(this,"Hãy điền lại thông tin!",Toast.LENGTH_LONG).show()
            }else{
                auth.createUserWithEmailAndPassword(userName,password)          // create user in firebase user
                    .addOnCompleteListener(this){ task ->
                        if (task.isSuccessful){
                            val id = auth.uid.toString()
                            Log.d("id",id)
                            userDAO.addUserToDocument(id,user)                  // add user to document
                            Toast.makeText(this,"Đăng ký thành công",Toast.LENGTH_LONG).show()
                            val handler = Handler()                 //automatic return to login page
                            handler.postDelayed(object :Runnable{
                                override fun run() {
                                    auth.signOut()
                                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                    finish()
                                }

                            },3000)
                        }else{
                            Toast.makeText(this,"Thất bại, hãy thử lại!"+task.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }

//    private fun addUserToDocument(userName: String, password: String, userID:String) {
//        val dp = Firebase.firestore
//        val user = hashMapOf(
//            "username" to userName,
//            "password" to password,
//            "productsInCart" to mutableListOf<Product>(),
//            "purchasedProducts" to mutableListOf<Product>()
//        )
//        dp.collection("Users")
//            .document(userID)
//            .set(user)
//            .addOnCompleteListener {
//                Log.d("addUser","Add user success: "+ userName)
//            }
//            .addOnFailureListener{
//                Log.d("addUser","Add user fail: "+it.message)
//            }
//    }

    private fun retunToLoginPage() {
        btn_return_login_page.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            auth.signOut()
            finish()
        }
    }
}