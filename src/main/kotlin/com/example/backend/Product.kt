package com.example.backend

data class Product(val data: ArrayList<ProductData>)

data class ProductData(
    val id: Long,
    val category: String,
    val title: String,
    val description: String,
    val price: Long,
    val texture: String,
    val wash: String,
    val place: String,
    val note: String,
    val story: String,
    val main_image: String,
    val images: ArrayList<String>,
    val variants: ArrayList<Variant>,
    val colors: ArrayList<Color>,
    val sizes: ArrayList<String>
)

data class Variant(val color_code: String, val size: String, val stock: Long)

data class Color(val code: String, val name: String)
