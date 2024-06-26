package com.torres.pokdex

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
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
    private lateinit var searchPokemon: EditText
    private lateinit var tipoSpinner1: Spinner
    private lateinit var tipoSpinner2: Spinner
    private val db = FirebaseFirestore.getInstance()
    private val pokemonList = mutableListOf<Pokemon>()
    private val fullPokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listapokemons)

        searchPokemon = findViewById(R.id.buscarPokemon)
        tipoSpinner1 = findViewById(R.id.tipoSpinner1)
        tipoSpinner2 = findViewById(R.id.tipoSpinner2)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonAdapter(this, pokemonList)
        recyclerView.adapter = adapter

        setupTipoSpinners()
        fetchPokemons()

        searchPokemon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPokemons()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterPokemons()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        tipoSpinner1.onItemSelectedListener = spinnerListener
        tipoSpinner2.onItemSelectedListener = spinnerListener
    }

    private fun setupTipoSpinners() {
        val tipos = listOf("Todos", "Fuego", "Agua", "Planta", "Eléctrico", "Hielo", "Lucha", "Veneno", "Tierra", "Volador", "Psíquico", "Bicho", "Roca", "Fantasma", "Dragón", "Siniestro", "Acero", "Hada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        tipoSpinner1.adapter = adapter
        tipoSpinner2.adapter = adapter
    }

    private fun fetchPokemons() {
        db.collection("pokemons")
            .orderBy("numero")
            .get()
            .addOnSuccessListener { result ->
                fullPokemonList.clear()
                for (document in result) {
                    val pokemon = document.toObject(Pokemon::class.java)
                    fullPokemonList.add(pokemon)
                }
                pokemonList.addAll(fullPokemonList)
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { ex ->
                println("Error: $ex")
            }
    }

    private fun filterPokemons() {
        val query = searchPokemon.text.toString()
        val selectedTipo1 = tipoSpinner1.selectedItem.toString()
        val selectedTipo2 = tipoSpinner2.selectedItem.toString()

        val filteredList = fullPokemonList.filter {
            (it.nombre.contains(query, ignoreCase = true) || it.numero.toString().contains(query)) &&
                    (selectedTipo1 == "Todos" || it.tipos.contains(selectedTipo1)) &&
                    (selectedTipo2 == "Todos" || it.tipos.contains(selectedTipo2))
        }
        pokemonList.clear()
        pokemonList.addAll(filteredList)
        adapter.notifyDataSetChanged()
    }
}
