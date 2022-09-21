package com.shopingapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.shopingapp.activity.ListProductActivity
import com.shopingapp.R
import com.shopingapp.activity.CartActivity
import com.shopingapp.adapter.AdapterProduct
import com.shopingapp.adapter.AdapterTest
import com.shopingapp.dao.interfaceCallback.ProductCallback
import com.shopingapp.dao.ProductDAO
import com.shopingapp.model.Product
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome( val context1:Context):Fragment() {
    private  var productDAO = ProductDAO()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewFlipper()
        readData()
        categoryChosen()
        openCart()
        setUpToolBar()
        searchProduct()
    }

    private fun searchProduct() {
        img_search_icon_toolbar.setOnClickListener {
            var productName = edt_search_toolbar.text.toString().trim()
            if (productName == ""){
                Toast.makeText(context1,"Điền tên sản phẩm",Toast.LENGTH_LONG).show()
            }else{
                var intent = Intent(activity,ListProductActivity::class.java)
                intent.putExtra("searchproduct",productName)
                startActivity(intent)
                edt_search_toolbar.text.clear()
            }
        }
    }

    private fun setUpToolBar() {
        activity?.setActionBar(home_toolbar)
        activity?.actionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun openCart() {
        layout_open_cart.setOnClickListener {
            startActivity(Intent(activity,CartActivity::class.java))
        }
        img_icon_cart_toolbar.setOnClickListener {
            startActivity(Intent(activity,CartActivity::class.java))
        }
    }

    private fun categoryChosen() {
        var intent = Intent(activity, ListProductActivity::class.java)
        category_clothes.setOnClickListener {
            intent.putExtra("type","Thời trang")
            startActivity(intent)
        }
        category_electronic.setOnClickListener {
            intent.putExtra("type","Đồ điện tử")
            startActivity(intent)
        }
        category_comestic.setOnClickListener {
            intent.putExtra("type","Mỹ phẩm")
            startActivity(intent)
        }
        category_food.setOnClickListener {
            intent.putExtra("type","Đồ ăn")
            startActivity(intent)
        }
    }

    private fun readData() {
        productDAO.getNewProduct(object : ProductCallback {
            override fun getCallBack(list: ArrayList<Product>) {
                var adapter = AdapterProduct(list,context1)
                recycleview_product.hasFixedSize()
                recycleview_product.layoutManager = GridLayoutManager(context1,2)
                recycleview_product.adapter = adapter
            }

        })
    }

    private fun fakeData() {
        var list = ArrayList<String>()
        for(i in 0..10){
            list.add("Alo")
        }
        var adapter = AdapterTest(list,context1)
        recycleview_product.hasFixedSize()
        recycleview_product.layoutManager = GridLayoutManager(context1,2)
        recycleview_product.adapter = adapter

    }

    private fun setUpViewFlipper(){
        var res = intArrayOf(R.drawable.view1,R.drawable.view2,R.drawable.view3,R.drawable.view4)
        for(i in res){
            var imgView = ImageView(context1)
            imgView.setImageResource(i)
            imgView.scaleType = ImageView.ScaleType.FIT_XY
            viewflipper.addView(imgView)
        }
        viewflipper.startFlipping()
        var anim = AnimationUtils.loadAnimation(context1,R.anim.viewflipper_anim)
        viewflipper.setInAnimation(anim)
    }
}