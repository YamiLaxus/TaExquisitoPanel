package com.phonedev.taexisitopanel.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.phonedev.taexisitopanel.MainActivity
import com.phonedev.taexisitopanel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        login()
        var pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        var user = pref.getString("usuario", "")
        var pass = pref.getString("pass", "")

        if (user!!.isNotEmpty() and pass!!.isNotEmpty()) {
            showHome()
            finish()
        } else {
            Toast.makeText(this, "Ingresa tu usuario y contraseña :3", Toast.LENGTH_SHORT).show()
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            disableUI()
            val user = binding.etUser.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val url =
                "http://192.168.1.105/android_taexquisito/login.php?email=$user&pass=$password"
            val queue = Volley.newRequestQueue(this)
            if (binding.etUser.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null, { response ->
                        Toast.makeText(this, "Bienvenido de vuelta", Toast.LENGTH_SHORT).show()
                        guardarSharedPreference()
                        showHome()
                        finish()
                    },
                    {
                        Toast.makeText(
                            this,
                            it.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        enableUI()
                    }
                )
                queue.add(jsonObjectRequest)
            } else {
                showAlert()
                enableUI()
            }
        }
        binding.btnSigUp.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    private fun guardarSharedPreference() {
        var pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        var editor = pref.edit()
        editor.putString("usuario", binding.etUser.text.toString().trim())
        editor.putString("pass", binding.etPassword.text.toString().trim())
        editor.commit()

    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error al autenticar el usuario, verifica tus datos o crea un usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun enableUI() {
        binding.btnLogin.isEnabled = true
        binding.btnSigUp.isEnabled = true
        binding.let { ui ->
            ui.etUser.isEnabled = true
            ui.etPassword.isEnabled = true
        }
    }

    private fun disableUI() {
        binding.btnLogin.isEnabled = false
        binding.btnSigUp.isEnabled = false
        binding.let { ui ->
            ui.etUser.isEnabled = false
            ui.etPassword.isEnabled = false
        }
    }
}