package com.shopingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.shopingapp.R
import com.shopingapp.fragment.FragmentHome
import com.shopingapp.fragment.FragmentMenu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBottomNavigation()
        changeFragment(FragmentHome(this))
    }

    private fun setUpBottomNavigation(){
        bottom_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home ->{ changeFragment(FragmentHome(this))
                    Log.d("testnav","home")}

                R.id.menu ->{
                    changeFragment(FragmentMenu(this))
                }
            }
            true
        }
    }

    private fun changeFragment(fragment:Fragment){
        var fragmentManager:FragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}