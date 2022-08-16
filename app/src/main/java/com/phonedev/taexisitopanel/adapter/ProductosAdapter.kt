package com.phonedev.taexisitopanel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.phonedev.taexisitopanel.model.Productos
import com.phonedev.taexisitopanel.R
import com.phonedev.taexisitopanel.databinding.ActivityProductsviewBinding
import com.phonedev.taexisitopanel.databinding.ItemViewBinding
import com.phonedev.taexisitopanel.databinding.ProfileViewBinding

class ProductosAdapter(private val productosList: List<Productos>) :
    RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    var onItemClick: ((Productos) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productosList[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = productosList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun render(productos: Productos) {
            val binding = ItemViewBinding.bind(itemView)
            Glide.with(binding.imgProduct)
                .load(productos.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imgProduct)
            binding.tvNombre.text = productos.nombre
            binding.tvTipoComida.text = productos.tipo_comida
            binding.tvPrecio.text = productos.precio
        }
    }
}