package com.phonedev.taexisitopanel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.phonedev.taexisitopanel.MainActivity
import com.phonedev.taexisitopanel.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        register()
    }

    private fun register() {
        binding.btnLogin.setOnClickListener {
            disableUI()
            val url = "http://192.168.1.105/android_taexquisito/insertar.php"
            val queue = Volley.newRequestQueue(this)
            if (binding.etName.text.isNotEmpty() && binding.etNombreNegocio.text.isNotEmpty() && binding.etTipo.text.isNotEmpty() && binding.etTelefono.text.isNotEmpty() && binding.etUser.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                var resultadoPost = object :
                    StringRequest(Request.Method.POST, url, Response.Listener<String> { response ->
                        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
                        goHome()
                        finish()
                    }, Response.ErrorListener { error ->
                        Toast.makeText(
                            this,
                            "Error no se puede crear la cuenta. $error",
                            Toast.LENGTH_SHORT
                        ).show()
                        enableUI()
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val parametros = HashMap<String, String>()
                        parametros.put("nombre", binding.etName.text.toString().trim())
                        parametros.put("email", binding.etUser.text.toString().trim())
                        parametros.put("pass", binding.etPassword.text.toString().trim())
                        parametros.put("telefono1", binding.etTelefono.text.toString().trim())
                        parametros.put(
                            "nombre_negocio",
                            binding.etNombreNegocio.text.toString().trim()
                        )
                        parametros.put("tipo_negocio", binding.etTipo.text.toString().trim())
                        parametros.put("direccion", binding.etDireccion.text.toString().trim())
                        parametros.put("facebook_link", binding.etFacebook.text.toString().trim())
                        parametros.put("img_profile", "")
                        parametros.put("img_mapp", "")
                        return parametros
                    }
                }
                queue.add(resultadoPost)
            } else {
                showAlert()
                enableUI()
            }
        }
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun goHome() {
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
        homeIntent.putExtra("nombre", binding.etName.text.toString())
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error al registrar usuario. Verifica los campos.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun enableUI() {
        binding.btnLogin.isEnabled = true
        binding.btnCancel.isEnabled = true
        binding.let { ui ->
            ui.etName.isEnabled = true
            ui.etNombreNegocio.isEnabled = true
            ui.etTipo.isEnabled = true
            ui.etDireccion.isEnabled = true
            ui.etTelefono.isEnabled = true
            ui.etFacebook.isEnabled = true
            ui.etUser.isEnabled = true
            ui.etPassword.isEnabled = true
        }
    }

    private fun disableUI() {
        binding.btnLogin.isEnabled = false
        binding.btnCancel.isEnabled = false
        binding.let { ui ->
            ui.etName.isEnabled = false
            ui.etNombreNegocio.isEnabled = false
            ui.etTipo.isEnabled = false
            ui.etDireccion.isEnabled = false
            ui.etTelefono.isEnabled = false
            ui.etFacebook.isEnabled = false
            ui.etUser.isEnabled = false
            ui.etPassword.isEnabled = false
        }
    }
}