package com.shopingapp.model

import java.io.Serializable

data class User(var username:String = "",var password:String="",var productsInCart:MutableList<Product> = mutableListOf(),var purchasedProducts:MutableList<Product> = mutableListOf() ):Serializable {
//    private var userName:String = ""
//        get() = field
//        set(value) {
//            this.userName = value
//        }
//    private var password:String = ""
//        get() = field
//        set(value) {
//            this.password = value
//        }
//
//    private var productsInCart:MutableList<Product> = mutableListOf()
//        get() = field
//        set(value) {
//            this.productsInCart = value
//        }
//    private var purchasedProducts:MutableList<Product> = mutableListOf()
//        get() = field
//        set(value) {
//            this.purchasedProducts = value
//        }
//
//    constructor(userName:String, password:String,productsOnSale:MutableList<Product> , productsInCart:MutableList<Product>, purchasedProducts:MutableList<Product> ):this(){
//        this.userName = userName
//        this.password = password
//        this.productsInCart = productsInCart
//        this.purchasedProducts = purchasedProducts
//    }
}