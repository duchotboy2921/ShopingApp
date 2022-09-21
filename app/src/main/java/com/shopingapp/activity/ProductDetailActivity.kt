package com.shopingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.shopingapp.R
import com.shopingapp.dao.ItemCartDAO
import com.shopingapp.dao.UserDAO
import com.shopingapp.dao.interfaceCallback.SuccessCallback
import com.shopingapp.dao.interfaceCallback.UserCallback
import com.shopingapp.model.Product
import com.shopingapp.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_detail.*

class ProductDetailActivity : AppCompatActivity() {
    private var itemCartDAO = ItemCartDAO()
    private var userDAO = UserDAO()
    private lateinit var user:User
    private lateinit var product:Product
    private var isInCart = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_detail)
        getData()
        addProductToCart()
    }

    private fun addProductToCart() {
        layout_add_product_to_cart.setOnClickListener {
            if (!isInCart){
                itemCartDAO.addProductToCart(product, object :SuccessCallback{
                    override fun getCallback(isSuccess: Boolean) {
                        if (isSuccess){
                            Toast.makeText(applicationContext,"Thêm sản phẩm vào giỏ hàng thành công",Toast.LENGTH_LONG).show()
                            isInCart = true
                        }else{
                            Toast.makeText(applicationContext,"Lỗi!",Toast.LENGTH_LONG).show()
                        }
                    }

                })
            }else{
                Toast.makeText(applicationContext,"Sản phẩm đã có trong giỏ hảng rùi!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getData() {
        var intent = intent
        product = intent.getSerializableExtra("product") as Product
        Log.d("testser",product.productName)
        txt_product_detail_name.text = product.productName
        txt_product_detail_price.text = product.price.toString()
        ratingbar_product_detail.rating = product.ratingStar.toFloat()
        var db = FirebaseStorage.getInstance()
        var imgRef = db.reference.child("images").child("products_image").child(product.imgURL)
        imgRef.downloadUrl.addOnSuccessListener {
            try {
                Picasso.with(this).load(it).into(img_product_detail)
            }catch (ex:Exception){
                Log.e("picasso",ex.message.toString())
            }
        }
        getUserData()
    }

    private fun getUserData(){
        userDAO.getCurrentUserInfo(object :UserCallback{
            override fun getCallback(user: User) {
                this@ProductDetailActivity.user = user
                checkIfProductAlreadyInCart()
            }
        })
    }
    fun checkIfProductAlreadyInCart(){
        isInCart = user.productsInCart.contains(product)
    }
}