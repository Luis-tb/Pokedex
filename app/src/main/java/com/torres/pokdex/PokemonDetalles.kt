package com.torres.pokdex

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

class PokemonDetalles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detalles)
        val pokemonNumero = findViewById<TextView>(R.id.Numero)
        val pokemonNombre = findViewById<TextView>(R.id.Nombre)
        val pokemonDescripcion = findViewById<TextView>(R.id.Descripcion)
        val pokemonTipos = findViewById<TextView>(R.id.Tipos)
        val pokemonMovimientos = findViewById<TextView>(R.id.Movimientos)
        val pokemonImagen = findViewById<ImageView>(R.id.pokemon_imagen)
        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")
        pokemon?.let {
            pokemonNumero.text = String.format("#%03d", it.numero)
            pokemonNombre.text = it.nombre.capitalizeEachWord()
            val descripcionSinSaltos = it.descripcion.replace("\n", " ")
            pokemonDescripcion.text = descripcionSinSaltos
            pokemonTipos.text = it.tipos.joinToString(", ")
            pokemonMovimientos.text = it.movimientos.joinToString("\n") { move ->
                "${move.nombre.capitalizeEachWord()} - ${move.nivel_aprendizaje} ${move.metodo}"
            }
            Picasso.get().load(it.imagen).into(pokemonImagen)
        }
    }

    fun String.capitalizeEachWord(): String {
        return this.split(" ").joinToString(" ") { it.capitalize() }
    }
}
