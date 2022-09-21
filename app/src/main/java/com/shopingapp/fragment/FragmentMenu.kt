package com.shopingapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.shopingapp.activity.AddProductActivity
import com.shopingapp.activity.ListProductActivity
import com.shopingapp.activity.LoginActivity
import com.shopingapp.R
import com.shopingapp.dao.UserDAO
import com.shopingapp.dao.interfaceCallback.UserCallback
import com.shopingapp.model.User
import kotlinx.android.synthetic.main.fragment_menu.*

class FragmentMenu(val context1:Context):Fragment() {
    private var userDAO = UserDAO()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_menu,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signOut()
        addProduct()
        openOnSale()
        openPurchaseProduct()
        getInfo()
    }

    private fun getInfo() {
        userDAO.getCurrentUserInfo(object :UserCallback{
            override fun getCallback(user: User) {
                txt_username.text = user.username
            }
        })
    }

    private fun openOnSale() {
        layout_onsale_products.setOnClickListener {
            var intent = Intent(activity, ListProductActivity::class.java)
            intent.putExtra("onsaleproduct",true)
            startActivity(intent)
        }
    }
    private fun openPurchaseProduct(){
        layout_purchased_products.setOnClickListener {
            var intent = Intent(activity, ListProductActivity::class.java)
            intent.putExtra("purchasedProducts",true)
            startActivity(intent)
        }
    }

    private fun addProduct() {
        layout_upload_product.setOnClickListener {
            startActivity(Intent(activity, AddProductActivity::class.java))
        }
    }

    private fun signOut() {
        var fAuth = FirebaseAuth.getInstance()
        layout_log_out.setOnClickListener {
            if(fAuth.currentUser!= null){
                fAuth.signOut()
            }
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}