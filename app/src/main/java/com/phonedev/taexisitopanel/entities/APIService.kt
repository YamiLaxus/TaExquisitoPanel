package com.phonedev.taexisitopanel.entities

import com.phonedev.taexisitopanel.model.PerfilItem
import com.phonedev.taexisitopanel.model.Productos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET("mostrar_productos.php")
    fun getProducts(): Call<List<Productos>>

    @GET
    fun getPerfil(@Url url:String): Call<List<PerfilItem>>
}