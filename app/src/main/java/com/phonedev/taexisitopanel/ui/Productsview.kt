package com.phonedev.taexisitopanel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.phonedev.taexisitopanel.adapter.ProductosAdapter
import com.phonedev.taexisitopanel.api.Constants.BASE_URL
import com.phonedev.taexisitopanel.databinding.ActivityProductsviewBinding
import com.phonedev.taexisitopanel.entities.APIService
import com.phonedev.taexisitopanel.model.Productos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Productsview : AppCompatActivity() {

    private lateinit var binding: ActivityProductsviewBinding

    private var productosList: List<Productos>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        getProducts()
    }

    private fun getProducts() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIService::class.java)

        val retrofitData = retrofitBuilder.getProducts()
        retrofitData.enqueue(object : Callback<List<Productos>?> {
            override fun onResponse(
                call: Call<List<Productos>?>,
                response: Response<List<Productos>?>
            ) {
                val responseBody = response.body()!!
                val adapter = ProductosAdapter(responseBody)

                productosList = responseBody
                binding.recyclerView.layoutManager = GridLayoutManager(this@Productsview, 1)
                binding.recyclerView.adapter = adapter

                adapter.onItemClick = {
                    val intent = Intent(this@Productsview, DetailsActivity::class.java)
                    intent.putExtra("product", it)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<List<Productos>?>, t: Throwable) {
                Toast.makeText(this@Productsview, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}