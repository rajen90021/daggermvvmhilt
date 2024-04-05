package com.retrofit.daggermvvmhiltretrofit.repo

import androidx.lifecycle.MutableLiveData
import com.retrofit.daggermvvmhiltretrofit.data.ProductDataModelItem
import com.retrofit.daggermvvmhiltretrofit.network.ProductInterface
import com.retrofit.daggermvvmhiltretrofit.utils.ProductResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productInterface: ProductInterface) {
    private val _mutableData : MutableLiveData<ProductResults<List<ProductDataModelItem>>> = MutableLiveData()
    val liveData = _mutableData

    suspend fun getData(){
        try {
            val response = productInterface.getAllProductData()
            if (response.isNotEmpty()) {
                _mutableData.postValue(ProductResults.Success(response))
            } else {
                _mutableData.postValue(ProductResults.Error("Somethings went to wrong"))
            }
        } catch (e: Exception) {
            _mutableData.postValue(ProductResults.Error("Failed to fetch products: ${e.message}"))
        }
    }

    suspend fun getProductDataWithFlow(): List<ProductDataModelItem> {
        return try {
            val response = productInterface.getAllProductData()
            response.ifEmpty {
                throw Exception("Empty response")
            }
        } catch (e: Exception) {
            throw Exception("Failed to fetch products: ${e.message}")
        }
    }


    private val _mutableStateFlow: MutableStateFlow<ProductResults<List<ProductDataModelItem>>> = MutableStateFlow(ProductResults.Loading())
    val mutableStateFlow: StateFlow<ProductResults<List<ProductDataModelItem>>> = _mutableStateFlow

    private val _loadingStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loadingStateFlow: StateFlow<Boolean> = _loadingStateFlow

    suspend fun getProductData() {
        _loadingStateFlow.value = true
        try {
            val response = productInterface.getAllProductData()
            if (response.isNotEmpty()) {
                _mutableStateFlow.value = ProductResults.Success(response)
            } else {
                _mutableStateFlow.value = ProductResults.Error("Something went wrong")
            }
        } catch (e: Exception) {
            _mutableStateFlow.value = ProductResults.Error("Failed to fetch products: ${e.message}")
        } finally {
            _loadingStateFlow.value = false
        }
    }
}