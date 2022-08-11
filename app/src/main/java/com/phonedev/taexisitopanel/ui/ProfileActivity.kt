package com.phonedev.taexisitopanel.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.phonedev.taexisitopanel.adapter.ProfileAdapter
import com.phonedev.taexisitopanel.api.Constants.BASE_URL
import com.phonedev.taexisitopanel.databinding.ActivityProfileBinding
import com.phonedev.taexisitopanel.entities.APIService
import com.phonedev.taexisitopanel.model.PerfilItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        getPerfil()
        click()
    }

    private fun click() {
        binding.btnLogOut.setOnClickListener {
            logOut()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun getPerfil() {
        val perfil = intent.getParcelableExtra<PerfilItem>("perfil")
        if (perfil != null) {
            binding.tvNombre.text = perfil.nombre
            binding.tvNombreTienda.text = perfil.nombre_negocio
            binding.tvDireccion.text = perfil.direccion
            binding.tvTelefono.text = perfil.telefono1
            Glide.with(binding.imgProfile).load(perfil.img_profile).circleCrop().into(binding.imgProfile)
            Glide.with(binding.imgMap).load(perfil.img_map).into(binding.imgMap)
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logOut() {
        val pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("usuario", "")
        editor.putString("pass", "")
        editor.commit()
    }
    
    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirma")
        builder.setMessage("Seguro desea salir?")
        builder.setPositiveButton("Sí, salir.", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
