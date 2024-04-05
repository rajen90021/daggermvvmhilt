package com.retrofit.daggermvvmhiltretrofit.network

import com.retrofit.daggermvvmhiltretrofit.data.ProductDataModelItem
import retrofit2.http.GET

interface ProductInterface {

    @GET("/products")
    suspend fun getAllProductData() : List<ProductDataModelItem>
}