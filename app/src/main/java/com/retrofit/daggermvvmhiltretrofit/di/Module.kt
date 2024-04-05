package com.retrofit.daggermvvmhiltretrofit.di

import android.content.Context
import com.retrofit.daggermvvmhiltretrofit.network.ProductInterface
import com.retrofit.daggermvvmhiltretrofit.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun getContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    @Provides
    @Singleton
    fun getProductInterface(retrofit: Retrofit) : ProductInterface{
        return retrofit.create(ProductInterface::class.java)
    }



}