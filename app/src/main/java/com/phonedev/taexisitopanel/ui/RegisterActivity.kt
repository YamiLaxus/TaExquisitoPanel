package com.phonedev.taexisitopanel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.phonedev.taexisitopanel.MainActivity
import com.phonedev.taexisitopanel.databinding.ActivityRegisterBinding
import com.phonedev.taexisitopanel.entities.Constants

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        click()
        register()
    }

    private fun click() {
        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }
    }

    private fun register() {
        binding.btnLogin.setOnClickListener {
            disableUI()
            if (binding.etName.text.isNotEmpty() && binding.etNombreNegocio.text.isNotEmpty() && binding.etTipo.text.isNotEmpty() && binding.etTelefono.text.isNotEmpty() && binding.etUser.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.etUser.text.toString().trim(),
                    binding.etPassword.text.toString().trim()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        val profileUpdates =
                            UserProfileChangeRequest.Builder()
                                .setDisplayName(binding.etName.text.toString().trim()).build()

                        user?.updateProfile(profileUpdates)

                        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                        val dataBase = Firebase.database

                        val map: Map<String, String> = mapOf(
                            Pair("name", binding.etName.text.toString().trim()),
                            Pair("nombreNegocio", binding.etNombreNegocio.text.toString().trim()),
                            Pair("tipo", binding.etTipo.text.toString().trim()),
                            Pair("direccion", binding.etDireccion.text.toString().trim()),
                            Pair("telefono", binding.etTelefono.text.toString().trim()),
                            Pair("email", binding.etUser.text.toString().trim()),
                        )

                        val userRef =
                            dataBase.getReference(Constants.USERT_PANEL_PATH).child(userId)
                                .setValue(map)
                                .addOnCompleteListener { ok ->
                                    if (ok.isSuccessful) {
                                        goHome()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Error en RealTime DataBase",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        enableUI()
                                    }
                                }
                    } else {
                        Toast.makeText(this, "Error en FirebaseAuth", Toast.LENGTH_SHORT).show()
                        enableUI()
                    }
                }
            } else {
                showAlert()
                enableUI()
            }
        }
    }

    private fun goHome() {
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
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
            ui.etUser.isEnabled = false
            ui.etPassword.isEnabled = false
        }
    }
}