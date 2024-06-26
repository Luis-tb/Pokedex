package com.torres.pokdex

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PokemonAdapter(private val context: Context, private val pokemonList: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.pokemonNumero.text = String.format("#%03d", pokemon.numero)
        holder.pokemonNombre.text = pokemon.nombre.capitalizeEachWord()
        Picasso.get().load(pokemon.imagen).into(holder.pokemonImagen)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PokemonDetalles::class.java).apply {
                putExtra("pokemon", pokemon)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = pokemonList.size

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImagen: ImageView = itemView.findViewById(R.id.pokemon_imagen)
        val pokemonNumero: TextView = itemView.findViewById(R.id.pokemon_numero)
        val pokemonNombre: TextView = itemView.findViewById(R.id.pokemon_nombre)
    }

    fun String.capitalizeEachWord(): String {
        return this.split(" ").joinToString(" ") { it.capitalize() }
    }
}
