package com.shopingapp.dao.interfaceCallback

import com.shopingapp.model.Product

interface ProductCallback {
    fun getCallBack(list:ArrayList<Product>)
}