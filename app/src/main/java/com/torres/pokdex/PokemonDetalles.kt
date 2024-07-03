package com.torres.pokdex

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import java.text.Normalizer

class PokemonDetalles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detalles)
        val pokemonNumero = findViewById<TextView>(R.id.Numero)
        val pokemonNombre = findViewById<TextView>(R.id.Nombre)
        val pokemonDescripcion = findViewById<TextView>(R.id.Descripcion)
        val movimientosTable = findViewById<TableLayout>(R.id.movimientos_table)
        val pokemonImagen = findViewById<ImageView>(R.id.pokemon_imagen)
        val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")
        pokemon?.let {
            pokemonNumero.text = String.format("#%03d", it.numero)
            pokemonNombre.text = it.nombre.capitalizeEachWord()
            pokemonDescripcion.text = it.descripcion.replace("\n", " ")
            val tipo1Imagen = findViewById<ImageView>(R.id.tipo1_imagen)
            val tipo2Imagen = findViewById<ImageView>(R.id.tipo2_imagen)
            if (it.tipos.size >= 1) {
                val tipo1 = it.tipos[0].normalizeString()
                val tipo1ResourceId = resources.getIdentifier(tipo1, "drawable", packageName)
                tipo1Imagen.setImageResource(tipo1ResourceId)
            }
            if (it.tipos.size >= 2) {
                val tipo2 = it.tipos[1].normalizeString()
                val tipo2ResourceId = resources.getIdentifier(tipo2, "drawable", packageName)
                tipo2Imagen.setImageResource(tipo2ResourceId)
            }
            Picasso.get().load(it.imagen).into(pokemonImagen)
            it.movimientos.sortedWith(compareBy({ it.metodo.toLowerCase() != "nivel" }, { it.nivel_aprendizaje }))
                .forEach { move ->
                    val row = TableRow(this)
                    val nombreView = TextView(this)
                    nombreView.text = move.nombre.capitalizeEachWord()
                    nombreView.setPadding(8, 8, 8, 8)
                    val nivelView = TextView(this)
                    nivelView.text = if (move.metodo.toLowerCase() == "nivel") {
                        move.nivel_aprendizaje.toString()
                    } else {
                        ""
                    }
                    nivelView.setPadding(8, 8, 8, 8)
                    val tipoImagenView = if (move.metodo.toLowerCase() == "nivel") {
                        TextView(this).apply {
                            text = "Nivel"
                            setPadding(8, 8, 8, 8)
                        }
                    } else {
                        ImageView(this).apply {
                            val tipoImagenResource = obtenerImagenMovimiento(move.metodo)
                            setImageResource(tipoImagenResource)
                            adjustViewBounds = true
                            layoutParams = TableRow.LayoutParams(
                                resources.getDimensionPixelSize(R.dimen.image_width),
                                resources.getDimensionPixelSize(R.dimen.image_height)
                            )
                        }
                    }
                    row.addView(nombreView)
                    row.addView(nivelView)
                    row.addView(tipoImagenView)
                    movimientosTable.addView(row)
                }
        }
    }

    fun String.capitalizeEachWord(): String {
        return this.split(" ").joinToString(" ") { it.capitalize() }
    }

    fun String.normalizeString(): String {
        return Normalizer.normalize(this, Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            .toLowerCase()
    }

    private fun obtenerImagenMovimiento(tipoMovimiento: String): Int {
        return when (tipoMovimiento.toLowerCase()) {
            "maquina" -> R.drawable.maquina
            else -> R.drawable.pokeball
        }
    }
}
