package com.phonedev.taexisitopanel.model

import android.os.Parcel
import android.os.Parcelable

data class PerfilItem(
    val direccion: String,
    val email: String,
    val facebook_link: String,
    val id: String,
    val img_map: String,
    val img_profile: String,
    val nombre: String,
    val nombre_negocio: String,
    val pass: String,
    val telefono1: String,
    val tipo_negocio: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )
    
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(direccion)
        parcel.writeString(email)
        parcel.writeString(facebook_link)
        parcel.writeString(id)
        parcel.writeString(img_map)
        parcel.writeString(img_profile)
        parcel.writeString(nombre)
        parcel.writeString(nombre_negocio)
        parcel.writeString(pass)
        parcel.writeString(telefono1)
        parcel.writeString(tipo_negocio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PerfilItem> {
        override fun createFromParcel(parcel: Parcel): PerfilItem {
            return PerfilItem(parcel)
        }

        override fun newArray(size: Int): Array<PerfilItem?> {
            return arrayOfNulls(size)
        }
    }

}