package com.shopingapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.shopingapp.R
import com.shopingapp.dao.ProductDAO
import com.shopingapp.dao.interfaceCallback.SuccessCallback
import com.shopingapp.model.Product
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.fragment_menu.*

class AddProductActivity : AppCompatActivity() {
    private lateinit var imgPath:Uri
    private var isImagePicked = false
    private lateinit var imgName:String
    lateinit var dialog:ProgressDialog
    private var typeCategory = "Thời trang"
    private var productDAO = ProductDAO()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        setUpSpinner()
        pickImage()
        addProduct()
        back()
    }

    private fun back() {
        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun setUpSpinner() {
        val categories = resources.getStringArray(R.array.category)
        var adapter = ArrayAdapter.createFromResource(this, R.array.category,android.R.layout.simple_spinner_dropdown_item)
        spinenr_category.adapter = adapter
        spinenr_category.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                typeCategory = categories[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun pickImage(){
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
            imgPath = it
            try {
                var bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imgPath)
                img_add_product_image.setImageBitmap(bitmap)
                isImagePicked = true
            }catch (ex:Exception){
                Log.e("pick_image",ex.toString())
            }
        }
        img_add_product_image.setOnClickListener {
            getContent.launch("image/*")
        }
    }
    private fun uploadImageToStorage(){
        var storage = FirebaseStorage.getInstance()
        var imgRef = storage.reference.child("images").child("products_image").child(imgName)
        imgRef.putFile(imgPath)
            .addOnSuccessListener {
                Log.d("upload_image", "success")
            }
            .addOnFailureListener{
                Log.e("upload_image","error")
                Toast.makeText(this,"Tải ảnh thất bại",Toast.LENGTH_LONG)
            }
            .addOnCompleteListener{
                Log.d("upload_image","complete")
                dialog.dismiss()
                Toast.makeText(this,"Thành công",Toast.LENGTH_LONG).show()
                clearData()
            }
    }
    private fun addProduct(){
        btn_add_product.setOnClickListener {
            dialog = ProgressDialog(this)
            dialog.setTitle("Đang thêm sản phẩm")
            dialog.show()
            var db = Firebase.firestore.collection("Products")
            imgName = db.document().id
            try {
                var productName = edt_add_product_name.text.toString()
                var quantity = edt_add_product_quantity.text.toString().toInt()
                var price = edt_add_product_price.text.toString().toDouble()
                var city = edt_add_product_city.text.toString()
                var uid = FirebaseAuth.getInstance().uid
                var product = Product(productName,price,0.0,quantity,city,imgName,uid.toString(),typeCategory)
                if(productName == ""|| quantity == 0 || price ==0.0 || city ==""||!isImagePicked){
                    Toast.makeText(this,"Điền đầy đủ thông tin",Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }else {
                    productDAO.addProduct(product,object :SuccessCallback{
                        override fun getCallback(isSuccess: Boolean) {
                            if(isSuccess){
                                uploadImageToStorage()
                            }else{
                                Toast.makeText(this@AddProductActivity,"Opps lỗi rồi!",Toast.LENGTH_LONG).show()
                                dialog.dismiss()
                            }
                        }
                    })
                }
            }catch (ex:Exception){
                Toast.makeText(this,ex.message,Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun clearData(){   //after adding product successfully
        edt_add_product_name.text.clear()
        edt_add_product_quantity.text.clear()
        edt_add_product_price.text.clear()
        edt_add_product_city.text.clear()
        img_add_product_image.setImageResource(R.drawable.add_image)
        isImagePicked = false
    }
}