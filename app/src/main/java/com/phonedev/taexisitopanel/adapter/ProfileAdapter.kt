package com.phonedev.taexisitopanel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.phonedev.taexisitopanel.R
import com.phonedev.taexisitopanel.model.PerfilItem

class ProfileAdapter(private var perfil: List<PerfilItem>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    var onItemClick: ((PerfilItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.profile_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = perfil[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = perfil.size

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun render(perfil: PerfilItem) {
            val img = itemView.findViewById<ImageView>(R.id.btnPerfil)
            Glide.with(img).load(perfil.img_profile).circleCrop().into(img)
        }
    }
}