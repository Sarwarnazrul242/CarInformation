package com.bignerdranch.android.carinformation.model

class CarData {
    var name : String? = null
    var info : String? = null
    var img : String? = null

    var price: String? = null
    var engineType: String? = null
    constructor(){}

    constructor(
        name: String?,
        info: String?,
        img : String?,

        price: String?,
        engineType: String?
    ){
        this.name = name
        this.info = info
        this.img = img

        this.price = price
        this.engineType = engineType
    }
}