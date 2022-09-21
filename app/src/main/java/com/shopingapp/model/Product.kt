package com.shopingapp.model

import java.io.Serializable

data class Product(var productName:String ="",var price:Double=0.0,var  ratingStar:Double = 0.0
                   ,var quantity:Int = 0,var productCity:String = "", var imgURL:String = "",var idShop:String= "",var type:String = ""):Serializable {
//    private var productName:String = ""
//        get()  = field
//        set(value){
//            this.productName = value
//        }
//    var productPrice:Double = 0.0
//        get()  = field
//        set(value){
//            this.productPrice = value
//        }
//    var ratingStar:Double = 0.0
//        get()  = field
//        set(value){
//            this.ratingStar = value
//        }
//    var quantity:Int = 0
//        get()  = field
//        set(value){
//            this.quantity = value
//        }
//    var productCity:String = ""
//        get()  = field
//        set(value){
//            this.productCity = value
//        }
//    var imgURL:String = ""
//        get()  = field
//        set(value){
//            this.imgURL = value
//        }
//    var idShop:String = ""
//        get()  = field
//        set(value){
//            this.idShop = value
//        }
//
//    var type:String = ""
//        get() = field
//        set(value) {
//            this.type = value
//        }
//
//    constructor( productName:String, productPrice:Double, ratingStar:Double,quantity:Int,productCity:String,imgURL:String,idShop:String,type:String  ):this(){
//        this.productName = productName
//        this.productPrice = productPrice
//        this.productCity = productCity
//        this.ratingStar = ratingStar
//        this.quantity = quantity
//        this.imgURL = imgURL
//        this.idShop = idShop
//        this.type = type
//   }


}