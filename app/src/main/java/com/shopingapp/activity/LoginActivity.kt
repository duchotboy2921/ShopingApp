package com.shopingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shopingapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        checkLogin()
        toRegisterActivity()
        login()
    }

    private fun login() {
        btn_login.setOnClickListener {
            val userName =  edt_username.text.toString()
            val password = edt_password.text.toString()
            if(userName.trim() == ""|| password.trim() == ""){
                Toast.makeText(this,"Điền đầy đủ thông tin đê!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                auth.signInWithEmailAndPassword(userName,password)
                    .addOnCompleteListener{
                        if(it.isSuccessful){
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this,"Đăng nhập thất bại!",Toast.LENGTH_LONG).show()
                            Log.e("login_error", it.toString())
                        }
                    }
                    .addOnFailureListener{
                        Log.e("login_error",it.message.toString())
                    }
            }
        }
    }

    private fun checkLogin() {
        if(auth.currentUser!= null){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun toRegisterActivity() {
        btn_to_register_page.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}