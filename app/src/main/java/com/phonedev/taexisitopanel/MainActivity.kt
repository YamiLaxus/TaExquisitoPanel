package com.phonedev.taexisitopanel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.phonedev.taexisitopanel.databinding.ActivityMainBinding
import com.phonedev.taexisitopanel.ui.AddingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()
        binding.txtUser.text = FirebaseAuth.getInstance().currentUser?.displayName.toString()

        click()
    }

    private fun click() {
        binding.btnIngresar.setOnClickListener {
            val i = Intent(this, AddingActivity::class.java)
            startActivity(i)
        }
    }
}