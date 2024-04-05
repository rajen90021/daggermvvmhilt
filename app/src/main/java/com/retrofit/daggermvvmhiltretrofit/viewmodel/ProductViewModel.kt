package com.retrofit.daggermvvmhiltretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.retrofit.daggermvvmhiltretrofit.data.ProductDataModelItem
import com.retrofit.daggermvvmhiltretrofit.repo.ProductRepository
import com.retrofit.daggermvvmhiltretrofit.utils.ProductResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    val data = productRepository.liveData
    fun getData(){
        viewModelScope.launch {
            productRepository.getData()
        }
    }





    private val _productsLiveData = MutableLiveData<ProductResults<List<ProductDataModelItem>>>()
    val productsLiveData: LiveData<ProductResults<List<ProductDataModelItem>>> = _productsLiveData

    init {
        fetchProducts()
    }
    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                val products = productRepository.getProductDataWithFlow()
                _productsLiveData.value = ProductResults.Success(products)
            } catch (e: Exception) {
                _productsLiveData.value = ProductResults.Error("Failed to fetch products: ${e.message}")
            }
        }
    }

}