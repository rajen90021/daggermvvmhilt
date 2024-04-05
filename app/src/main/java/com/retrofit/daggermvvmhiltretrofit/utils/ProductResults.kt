package com.retrofit.daggermvvmhiltretrofit.utils

sealed class ProductResults <T> (val data : T ?= null , val message : String ?= null) {
    class Loading<T>() : ProductResults<T>()
    class Success<T>(data: T?= null) : ProductResults<T>(data = data)
    class Error<T>(message: String?= null) : ProductResults<T>(message = message)
}