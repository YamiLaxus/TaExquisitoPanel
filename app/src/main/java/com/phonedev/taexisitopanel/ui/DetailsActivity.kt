package com.phonedev.taexisitopanel.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.phonedev.taexisitopanel.adapter.ProductosAdapter
import com.phonedev.taexisitopanel.api.Constants
import com.phonedev.taexisitopanel.databinding.ActivityDetailsBinding
import com.phonedev.taexisitopanel.entities.APIService
import com.phonedev.taexisitopanel.model.Productos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val product = intent.getParcelableExtra<Productos>( "product")
        if (product != null){
            binding.tvNombre.text = product.nombre
            Glide.with(binding.imgProduct).load(product.img).into(binding.imgProduct)
        }
    }
}