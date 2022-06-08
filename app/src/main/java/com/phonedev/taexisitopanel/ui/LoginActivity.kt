package com.phonedev.taexisitopanel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
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

        click()
    }

    private fun click() {
        binding.btnSigUp.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
        binding.btnLogin.setOnClickListener {
            disableUI()
            if (binding.etUser.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.etUser.text.toString(),
                    binding.etPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val homeIntent = Intent(this, MainActivity::class.java)
                        startActivity(homeIntent)
                        this.finish()
                    } else {
                        showAlert()
                        enableUI()
                    }
                }
            } else {
                showAlert()
                enableUI()
            }
        }
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