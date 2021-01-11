package com.dicoding.pegawaiapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    private const val BASE_URL = "http://192.168.0.123/SEKOLAH/xii_sm2/crud_pegawai/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pegawaiServices: PegawaiServices = retrofit.create(PegawaiServices::class.java)
}