package com.phonedev.taexisitopanel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.phonedev.taexisitopanel.adapter.ProfileAdapter
import com.phonedev.taexisitopanel.api.Constants.BASE_URL
import com.phonedev.taexisitopanel.databinding.ActivityMainBinding
import com.phonedev.taexisitopanel.entities.APIService
import com.phonedev.taexisitopanel.model.PerfilItem
import com.phonedev.taexisitopanel.ui.AddingActivity
import com.phonedev.taexisitopanel.ui.Productsview
import com.phonedev.taexisitopanel.ui.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var perfilList: List<PerfilItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        val pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        val usuario = pref.getString("usuario", "")
        binding.txtUser.text = usuario!!

        click()
        getProfile()
    }

    private fun click() {
        binding.btnIngresar.setOnClickListener {
            val i = Intent(this, AddingActivity::class.java)
            startActivity(i)
        }
        binding.btnConsultar.setOnClickListener {
            val i = Intent(this, Productsview::class.java)
            startActivity(i)
        }
    }

    private fun getProfile() {
        val pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        val user = pref.getString("usuario", "")
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIService::class.java)

        val retrofitData = retrofitBuilder.getPerfil("perfil.php?email=${user.toString()}")
        retrofitData.enqueue(object : Callback<List<PerfilItem>?> {
            override fun onResponse(
                call: Call<List<PerfilItem>?>,
                response: Response<List<PerfilItem>?>
            ) {
                val responseBody = response.body()!!
                val adapter = ProfileAdapter(responseBody)
                perfilList = responseBody
                binding.rvProfile.layoutManager = GridLayoutManager(this@MainActivity, 1)
                binding.rvProfile.adapter = adapter

                adapter.onItemClick = {
                    val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                    intent.putExtra("perfil", it)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<List<PerfilItem>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}