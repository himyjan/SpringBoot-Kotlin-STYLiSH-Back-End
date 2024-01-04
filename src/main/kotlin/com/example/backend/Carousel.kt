package com.example.backend

data class Carousel(val data: ArrayList<CarouselData>)

data class CarouselData(val id: Long, val product_id: Long, val picture: String, val story: String)
