package com.shopingapp.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopingapp.R
import com.shopingapp.adapter.ApdapterItemCart
import com.shopingapp.dao.ItemCartDAO
import com.shopingapp.dao.UserDAO
import com.shopingapp.dao.interfaceCallback.ItemClickListener
import com.shopingapp.dao.interfaceCallback.SuccessCallback
import com.shopingapp.dao.interfaceCallback.UserCallback
import com.shopingapp.model.Product
import com.shopingapp.model.User
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {
    private var userDAO = UserDAO()
    private var itemCartDAO = ItemCartDAO()
    private var checkedProducts = ArrayList<Product>()
    private var total:Double =0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        getData()
        checkOut()
        //testDelete()
    }

    private fun getData() {                                 // get user to get list product in cart
        userDAO.getCurrentUserInfo(object :UserCallback{
            override fun getCallback(user: User) {
                if (user.productsInCart.size ==0){
                    textview_cartinfo.visibility = View.VISIBLE             // display text if cart is empty
                }else{
                    textview_cartinfo.visibility = View.GONE
                }
                var adapter = ApdapterItemCart(user.productsInCart,this@CartActivity,object :ItemClickListener{
                    override fun onItemDeleteClick(product: Product, position: Int) {
                        var alertDialogBuilder = AlertDialog.Builder(this@CartActivity)
                        alertDialogBuilder.setTitle("Bạn có muốn xóa sản phẩm "+product.productName+" khỏi giỏ hàng?")
                        alertDialogBuilder.setPositiveButton("Có",object :DialogInterface.OnClickListener{          // click có -> delete product
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                itemCartDAO.deleteProductFromCart(product,object :SuccessCallback{
                                    override fun getCallback(isSuccess: Boolean) {
                                        if(isSuccess){
                                            Toast.makeText(this@CartActivity,"Thành công",Toast.LENGTH_LONG).show()     // if delete product success -> toast
                                            getData()                                                                               // refresh data
                                        }else{
                                            Toast.makeText(this@CartActivity,"Lỗi rồi!",Toast.LENGTH_LONG).show()
                                        }
                                    }
                                })

                            }
                        })
                        alertDialogBuilder.setNegativeButton("Không",object :DialogInterface.OnClickListener{
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0?.cancel()
                            }

                        })
                        alertDialogBuilder.create().show()
                    }

                    override fun onItemCheckClick(product: Product, isCheck: Boolean) {
                        if(isCheck){
                            //Toast.makeText(this@CartActivity,product.productName+" "+isCheck.toString(),Toast.LENGTH_LONG).show()
                            checkedProducts.add(product)
                            total +=product.price
                            txt_total_cart.text = total.toString()+"đ"
                        }else{
                            checkedProducts.remove(product)
                            total-= product.price
                            txt_total_cart.text = total.toString()+"đ"
                        }
                    }

                })
                recycleview_cart.hasFixedSize()
                recycleview_cart.layoutManager = LinearLayoutManager(this@CartActivity)
                recycleview_cart.adapter = adapter
            }
        })
    }

    private fun checkOut() {
        layout_checkout.setOnClickListener {
            if (checkedProducts.size==0){
                Toast.makeText(this@CartActivity,"Chưa chọn sản phẩm nào",Toast.LENGTH_LONG).show()
            }else{
                var alertDialogBuilder = AlertDialog.Builder(this@CartActivity)
                alertDialogBuilder.setTitle("Bạn có muốn thanh toán các mặt hàng với giá trị $total đ ?")         // create a dialog to ask again
                alertDialogBuilder.setNegativeButton("Không", object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0?.cancel()
                    }
                })
                alertDialogBuilder.setPositiveButton("Có",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        var arr = Array<Product>(checkedProducts.size){checkedProducts[it]}
                        itemCartDAO.deleteListProductFromCart(arr, object :SuccessCallback{                     //delete products in cart
                            override fun getCallback(isSuccess: Boolean) {
                                if (isSuccess){
                                    Toast.makeText(this@CartActivity,"Thành công",Toast.LENGTH_LONG).show()
                                    txt_total_cart.text = "0đ"
                                    getData()                                                                     // refresh data if success
                                }else{
                                    Toast.makeText(this@CartActivity,"Lỗi rồi",Toast.LENGTH_LONG)
                                }
                            }
                        })
                        itemCartDAO.addProductToPurchasedList(arr)                                             // add deleted products to purchased list
                    }
                })
                alertDialogBuilder.create().show()                  // show the dialog
            }
        }
    }
}