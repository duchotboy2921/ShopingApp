package com.shopingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopingapp.R
import com.shopingapp.adapter.AdapterProduct
import com.shopingapp.dao.interfaceCallback.ProductCallback
import com.shopingapp.dao.ProductDAO
import com.shopingapp.dao.UserDAO
import com.shopingapp.dao.interfaceCallback.UserCallback
import com.shopingapp.model.Product
import com.shopingapp.model.User
import kotlinx.android.synthetic.main.activity_list_product.*

class ListProductActivity : AppCompatActivity() {
    private var productDAO = ProductDAO()
    private var userDAO = UserDAO()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)
        setUpBanner()
        getData()
    }

    private fun setUpBanner() {
        var intent = intent
        var typeCategory:String? = intent.getStringExtra("type")
        when(typeCategory){
            "Đồ ăn"-> img_banner_list_product.setImageResource(R.drawable.food)
            "Mỹ phẩm"-> img_banner_list_product.setImageResource(R.drawable.comestic)
            "Thời trang"-> img_banner_list_product.setImageResource(R.drawable.clothes)
            "Đồ điện tử"-> img_banner_list_product.setImageResource(R.drawable.electronic)
        }
    }

    private fun getData() {
        var intent = intent
        var typeCategory:String? = intent.getStringExtra("type")      // get category type from intent
        var onSaleProduct:Boolean = intent.getBooleanExtra("onsaleproduct",false)
        var purchasedProducts:Boolean = intent.getBooleanExtra("purchasedProducts",false)
        var productName: String? = intent.getStringExtra("searchproduct")
        Log.d("testsize",onSaleProduct.toString())
        if( typeCategory != null){
            textview_category_type.text = typeCategory
            productDAO.getProductByType(typeCategory,object : ProductCallback {
                override fun getCallBack(list: ArrayList<Product>) {
                    var adapter = AdapterProduct(list,this@ListProductActivity)
                    recycleview_product_category.hasFixedSize()
                    recycleview_product_category.layoutManager = GridLayoutManager(this@ListProductActivity,2)
                    recycleview_product_category.adapter = adapter
                }
            })
        }
        if(onSaleProduct){
            textview_category_type.text = "Sản phẩm đăng bán"               // if request list on sale products
            productDAO.getOnSaleProduct(object : ProductCallback {
                override fun getCallBack(list: ArrayList<Product>) {
                    var adapter = AdapterProduct(list,this@ListProductActivity)
                    Log.d("testSize",list.size.toString())
                    recycleview_product_category.hasFixedSize()
                    recycleview_product_category.layoutManager = GridLayoutManager(this@ListProductActivity,2)
                    recycleview_product_category.adapter = adapter
                }
            })
        }
        if (purchasedProducts){                                             // if request list purchased products
            textview_category_type.text = "Sản phẩm đã mua"
            userDAO.getCurrentUserInfo(object :UserCallback{
                override fun getCallback(user: User) {
                    var list:ArrayList<Product> = user.purchasedProducts as ArrayList<Product>
                    var adapter = AdapterProduct(list,this@ListProductActivity)
                    recycleview_product_category.hasFixedSize()
                    recycleview_product_category.layoutManager = GridLayoutManager(this@ListProductActivity,2)
                    recycleview_product_category.adapter = adapter
                }
            })
        }
        if (productName!= null){
            textview_category_type.text = "Tìm kiếm sản phẩm: $productName"
            productDAO.getProductByName(productName, object :ProductCallback{
                override fun getCallBack(list: ArrayList<Product>) {
                    var adapter = AdapterProduct(list,this@ListProductActivity)
                    Log.d("testSize",list.size.toString())
                    recycleview_product_category.hasFixedSize()
                    recycleview_product_category.layoutManager = GridLayoutManager(this@ListProductActivity,2)
                    recycleview_product_category.adapter = adapter
                }
            })
        }
    }
}