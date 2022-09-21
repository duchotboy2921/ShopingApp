package com.shopingapp.dao.interfaceCallback

import com.shopingapp.model.Product

interface ItemClickListener {
    fun onItemDeleteClick(product:Product, position: Int)
    fun onItemCheckClick(product: Product,isCheck:Boolean)
}