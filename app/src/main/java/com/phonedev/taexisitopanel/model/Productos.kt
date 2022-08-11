package com.phonedev.taexisitopanel.model

import android.os.Parcel
import android.os.Parcelable

data class Productos(
    val nombre: String,
    val precio: String,
    val descripcion: String,
    val telefono: String,
    val tipo_comida: String,
    val nombre_negocio: String,
    val direccion: String,
    val facebook_link: String,
    val tiempo_entrega: String,
    val img: String
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
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(precio)
        parcel.writeString(descripcion)
        parcel.writeString(telefono)
        parcel.writeString(tipo_comida)
        parcel.writeString(nombre_negocio)
        parcel.writeString(direccion)
        parcel.writeString(facebook_link)
        parcel.writeString(tiempo_entrega)
        parcel.writeString(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Productos> {
        override fun createFromParcel(parcel: Parcel): Productos {
            return Productos(parcel)
        }

        override fun newArray(size: Int): Array<Productos?> {
            return arrayOfNulls(size)
        }
    }
}


