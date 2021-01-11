package com.dicoding.pegawaiapp.network

import com.dicoding.pegawaiapp.model.PegawaiResult
import retrofit2.Response
import retrofit2.http.*

interface PegawaiServices {
    @GET("tampilPgw.php")
    suspend fun getPegawaiById(@Query("id") id : Int) : PegawaiResult

    @FormUrlEncoded
    @POST("tambahPgw.php")
    suspend fun addPegawai(
        @Field("name")  name : String,
        @Field("desg")  desg : String,
        @Field("salary")  salary : Int,
    ) : Response<String>

    @GET("tampilSemuaPgw.php")
    suspend fun getAllPegawai() : PegawaiResult

    @FormUrlEncoded
    @POST("updatePgw.php")
    suspend fun updatePegawai(
        @Field("id")  id : Int,
        @Field("name")  name : String,
        @Field("desg")  desg : String,
        @Field("salary")  salary : Int,
    ) : Response<String>
    
    @GET("hapusPgw.php")
    suspend fun hapusPegawai(@Query("id") id : Int) : Response<String>
}