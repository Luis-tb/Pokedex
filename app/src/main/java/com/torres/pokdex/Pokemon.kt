package com.torres.pokdex

import android.os.Parcel
import android.os.Parcelable

data class Pokemon(
    val numero: Int = 0,
    val nombre: String = "",
    val imagen: String = "",
    val descripcion: String = "",
    val tipos: List<String> = emptyList(),
    val movimientos: List<Movimiento> = emptyList()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createTypedArrayList(Movimiento.CREATOR) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(numero)
        parcel.writeString(nombre)
        parcel.writeString(imagen)
        parcel.writeString(descripcion)
        parcel.writeStringList(tipos)
        parcel.writeTypedList(movimientos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}

data class Movimiento(
    val nombre: String = "",
    val nivel_aprendizaje: Int = 0,
    val metodo: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(nivel_aprendizaje)
        parcel.writeString(metodo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movimiento> {
        override fun createFromParcel(parcel: Parcel): Movimiento {
            return Movimiento(parcel)
        }

        override fun newArray(size: Int): Array<Movimiento?> {
            return arrayOfNulls(size)
        }
    }
}
data class Move(
    val nombre: String,
    val nivelAprendizaje: Int,
    val metodo: String // Puede ser "nivel" o "maquina", u otros tipos de métodos
)
