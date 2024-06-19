package com.torres.pokdex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
enum class TipoProvider {
    BASIC,
    GOOGLE
}

class listapokemons : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private val db = FirebaseFirestore.getInstance()
    private val pokemonList = mutableListOf<Pokemon>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listapokemons)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PokemonAdapter(this, pokemonList)
        recyclerView.adapter = adapter

        fetchPokemons()
    }
    private fun fetchPokemons() {
        db.collection("pokemons")
            .orderBy("numero")
            .get()
            .addOnSuccessListener { result ->
                pokemonList.clear()
                for (document in result) {
                    val pokemon = document.toObject(Pokemon::class.java)
                    pokemonList.add(pokemon)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { ex ->
                println("Error: $ex")
            }
    }

}