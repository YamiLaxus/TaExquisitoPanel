package com.phonedev.taexisitopanel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phonedev.taexisitopanel.model.Productos
import com.phonedev.taexisitopanel.R

class ProductosAdapter(private val productosList: List<Productos>) :
    RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    var onItemClick: ((Productos) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.post_item_container, parent, false))
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
            val img = itemView.findViewById<ImageView>(R.id.imbPost)
            Glide.with(img).load(productos.img).into(img)
        }
    }
}