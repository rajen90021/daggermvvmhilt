package com.retrofit.daggermvvmhiltretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.retrofit.daggermvvmhiltretrofit.databinding.ActivityMainBinding
import com.retrofit.daggermvvmhiltretrofit.utils.ProductResults
import com.retrofit.daggermvvmhiltretrofit.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        getProductDataWithTheHelpOfFlow()
    }

    private fun getDataWithTheHelpOfLiveData() {
        viewModel.getData()

        viewModel.data.observe(this){
            when(it){
                is ProductResults.Loading -> {
                    Log.d("dataCowmsa","Loading")
                    binding.textData.text = "Loading"
                }

                is ProductResults.Success -> {
                    Log.d("dataCowmsa",it.data.toString())
                    binding.textData.text = it.data.toString()
                }

                is ProductResults.Error -> {
                    Log.d("dataCowmsa",it.message.toString())
                    binding.textData.text = "Error"
                }
            }
        }
    }

    private fun getProductDataWithTheHelpOfFlow() {
        viewModel.productsLiveData.observe(this){
            when(it){
                is ProductResults.Loading -> {
                    Log.d("dataCowmsa","Loading")
                    binding.textData.text = "Loading"
                }

                is ProductResults.Success -> {
                    Log.d("dataCowmsa",it.data.toString())
                    binding.textData.text = it.data.toString()
                }

                is ProductResults.Error -> {
                    Log.d("dataCowmsa",it.message.toString())
                    binding.textData.text = "Error"
                }
            }
        }
    }
}