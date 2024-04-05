package com.retrofit.daggermvvmhiltretrofit.data

data class ProductDataModelItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)