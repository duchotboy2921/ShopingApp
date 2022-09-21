package com.shopingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.shopingapp.R
import kotlinx.coroutines.Runnable

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        var handler =Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                startActivity(Intent(this@IntroActivity,LoginActivity::class.java))
                finish()
            }

        },3000)
    }
}