package com.phonedev.taexisitopanel.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.phonedev.taexisitopanel.databinding.ActivityAddingBinding
import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AddingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddingBinding
    private var photoSelectedUri: Uri? = null

    private lateinit var option: Spinner
    var selected: String? = null

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { photoSelected ->
            if (photoSelected.resultCode == Activity.RESULT_OK) {
                photoSelectedUri = photoSelected.data?.data

                binding.let {
                    Glide.with(this)
                        .load(photoSelectedUri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(it.imgProductPrevie)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        title = "Agregar un producto"

        setButtons()
        selectLine()
    }

    private fun setButtons() {
        binding.btnSave.setOnClickListener {
            disableUI()
            val url = "http://192.168.1.105/android_taexquisito/ingresar_producto.php"
            val queue = Volley.newRequestQueue(this)
            if (binding.etName.text!!.isNotEmpty() && binding.etPrice.text!!.isNotEmpty() && binding.etDesciption.text!!.isNotEmpty() && binding.etPhone.text!!.isNotEmpty() && binding.etDireccion.text!!.isNotEmpty() && binding.etNombreTienda.text!!.isNotEmpty()) {
                val resultadoPost = object :
                    StringRequest(Method.POST, url, Response.Listener { response ->
                        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                        onBackPressed()
                        finish()
                    }, Response.ErrorListener { error ->
                        Toast.makeText(
                            this,
                            error.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        enableUI()
                    }) {
                    override fun getParams(): MutableMap<String, String> {
                        val pref = getSharedPreferences("user", Context.MODE_PRIVATE)
                        val usuario = pref.getString("usuario", "")
                        val parametros = HashMap<String, String>()
                        parametros["nombre"] = binding.etName.text.toString().trim()
                        parametros["precio"] = binding.etPrice.text.toString().trim()
                        parametros["descripcion"] = binding.etDesciption.text.toString().trim()
                        parametros["telefono"] = binding.etPhone.text.toString().trim()
                        parametros["tipo_comida"] = selected.toString()
                        parametros["nombre_negocio"] = binding.etNombreTienda.text.toString().trim()
                        parametros["direccion"] = binding.etDireccion.text.toString().trim()
                        parametros["facebook_link"] = binding.etFacebook.text.toString().trim()
                        parametros["tiempo_entrega"] = binding.etTiempoEntrega.text.toString().trim()
                        parametros["usuario"] = usuario.toString()
                        parametros["img"] = "https://cdni.russiatoday.com/actualidad/public_images/2022.02/article/620bddd359bf5b49bc7131b9.jpg"
                        return parametros
                    }
                }
                queue.add(resultadoPost)
            } else {
                showAlert()
                enableUI()
            }
        }
        binding.ibProduct.setOnClickListener {
            openGallery()
        }
        binding.btnCancel.setOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(intent)
    }

    private fun selectLine() {
        option = binding.tipoSpinner

        val options = arrayOf(
            "BEBIDA CALIENTE",
            "BEBIDA FRIA",
            "COMIDA RAPIDA",
            "COMIDA COMPLETO",
            "POSTRES",
            "GOLOCINAS"
        )
        option.adapter = ArrayAdapter(this, R.layout.simple_expandable_list_item_1, options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected = options[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error al ingresar el producto. Verifica los campos.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun disableUI() {
        binding.etName.isEnabled = false
        binding.etDesciption.isEnabled = false
        binding.etPrice.isEnabled = false
        binding.spinnerTipo.isEnabled = false
        binding.etPhone.isEnabled = false
        binding.etFacebook.isEnabled = false
        binding.etNombreTienda.isEnabled = false
        binding.etTiempoEntrega.isEnabled = false
        binding.btnSave.isEnabled = false
        binding.btnCancel.isEnabled = false
    }

    private fun enableUI() {
        binding.etName.isEnabled = true
        binding.etDesciption.isEnabled = true
        binding.etPrice.isEnabled = true
        binding.spinnerTipo.isEnabled = true
        binding.etPhone.isEnabled = true
        binding.etFacebook.isEnabled = true
        binding.etNombreTienda.isEnabled = true
        binding.etTiempoEntrega.isEnabled = true
        binding.btnSave.isEnabled = true
        binding.btnCancel.isEnabled = true
    }
}