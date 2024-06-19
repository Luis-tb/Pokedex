package com.torres.pokdex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

enum class TipoProvider {
    BASIC,
    GOOGLE
}

class listapokemons : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private lateinit var pokemonList: List<Pokemon>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listapokemons)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        pokemonList = listOf(
            Pokemon(1, "Bulbasaur", "https://img.pokemondb.net/artwork/bulbasaur.jpg"),
            Pokemon(2, "Ivysaur", "https://img.pokemondb.net/artwork/ivysaur.jpg"),
            Pokemon(3, "Venusaur", "https://img.pokemondb.net/artwork/venusaur.jpg"),
            Pokemon(4, "Charmander", "https://img.pokemondb.net/artwork/charmander.jpg"),
            Pokemon(5, "Charmeleon", "https://img.pokemondb.net/artwork/charmeleon.jpg"),
            Pokemon(6, "Charizard", "https://img.pokemondb.net/artwork/charizard.jpg"),
            Pokemon(7, "Squirtle", "https://img.pokemondb.net/artwork/squirtle.jpg"),
            Pokemon(8, "Wartortle", "https://img.pokemondb.net/artwork/wartortle.jpg"),
            Pokemon(9, "Blastoise", "https://img.pokemondb.net/artwork/blastoise.jpg"),
            Pokemon(10, "Caterpie", "https://img.pokemondb.net/artwork/caterpie.jpg"),
            Pokemon(11, "Metapod", "https://img.pokemondb.net/artwork/metapod.jpg"),
            Pokemon(12, "Butterfree", "https://img.pokemondb.net/artwork/butterfree.jpg"),
            Pokemon(13, "Weedle", "https://img.pokemondb.net/artwork/weedle.jpg"),
            Pokemon(14, "Kakuna", "https://img.pokemondb.net/artwork/kakuna.jpg"),
            Pokemon(15, "Beedrill", "https://img.pokemondb.net/artwork/beedrill.jpg"),
            Pokemon(16, "Pidgey", "https://img.pokemondb.net/artwork/pidgey.jpg"),
            Pokemon(17, "Pidgeotto", "https://img.pokemondb.net/artwork/pidgeotto.jpg"),
            Pokemon(18, "Pidgeot", "https://img.pokemondb.net/artwork/pidgeot.jpg"),
            Pokemon(19, "Rattata", "https://img.pokemondb.net/artwork/rattata.jpg"),
            Pokemon(20, "Raticate", "https://img.pokemondb.net/artwork/raticate.jpg"),
            Pokemon(21, "Spearow", "https://img.pokemondb.net/artwork/spearow.jpg"),
            Pokemon(22, "Fearow", "https://img.pokemondb.net/artwork/fearow.jpg"),
            Pokemon(23, "Ekans", "https://img.pokemondb.net/artwork/ekans.jpg"),
            Pokemon(24, "Arbok", "https://img.pokemondb.net/artwork/arbok.jpg"),
            Pokemon(25, "Pikachu", "https://img.pokemondb.net/artwork/pikachu.jpg"),
            Pokemon(26, "Raichu", "https://img.pokemondb.net/artwork/raichu.jpg"),
            Pokemon(27, "Sandshrew", "https://img.pokemondb.net/artwork/sandshrew.jpg"),
            Pokemon(28, "Sandslash", "https://img.pokemondb.net/artwork/sandslash.jpg"),
            Pokemon(29, "Nidoran♀", "https://img.pokemondb.net/artwork/nidoran-f.jpg"),
            Pokemon(30, "Nidorina", "https://img.pokemondb.net/artwork/nidorina.jpg"),
            Pokemon(31, "Nidoqueen", "https://img.pokemondb.net/artwork/nidoqueen.jpg"),
            Pokemon(32, "Nidoran♂", "https://img.pokemondb.net/artwork/nidoran-m.jpg"),
            Pokemon(33, "Nidorino", "https://img.pokemondb.net/artwork/nidorino.jpg"),
            Pokemon(34, "Nidoking", "https://img.pokemondb.net/artwork/nidoking.jpg"),
            Pokemon(35, "Clefairy", "https://img.pokemondb.net/artwork/clefairy.jpg"),
            Pokemon(36, "Clefable", "https://img.pokemondb.net/artwork/clefable.jpg"),
            Pokemon(37, "Vulpix", "https://img.pokemondb.net/artwork/vulpix.jpg"),
            Pokemon(38, "Ninetales", "https://img.pokemondb.net/artwork/ninetales.jpg"),
            Pokemon(39, "Jigglypuff", "https://img.pokemondb.net/artwork/jigglypuff.jpg"),
            Pokemon(40, "Wigglytuff", "https://img.pokemondb.net/artwork/wigglytuff.jpg"),
            Pokemon(41, "Zubat", "https://img.pokemondb.net/artwork/zubat.jpg"),
            Pokemon(42, "Golbat", "https://img.pokemondb.net/artwork/golbat.jpg"),
            Pokemon(43, "Oddish", "https://img.pokemondb.net/artwork/oddish.jpg"),
            Pokemon(44, "Gloom", "https://img.pokemondb.net/artwork/gloom.jpg"),
            Pokemon(45, "Vileplume", "https://img.pokemondb.net/artwork/vileplume.jpg"),
            Pokemon(46, "Paras", "https://img.pokemondb.net/artwork/paras.jpg"),
            Pokemon(47, "Parasect", "https://img.pokemondb.net/artwork/parasect.jpg"),
            Pokemon(48, "Venonat", "https://img.pokemondb.net/artwork/venonat.jpg"),
            Pokemon(49, "Venomoth", "https://img.pokemondb.net/artwork/venomoth.jpg"),
            Pokemon(50, "Diglett", "https://img.pokemondb.net/artwork/diglett.jpg"),
            Pokemon(51, "Dugtrio", "https://img.pokemondb.net/artwork/dugtrio.jpg"),
            Pokemon(52, "Meowth", "https://img.pokemondb.net/artwork/meowth.jpg"),
            Pokemon(53, "Persian", "https://img.pokemondb.net/artwork/persian.jpg"),
            Pokemon(54, "Psyduck", "https://img.pokemondb.net/artwork/psyduck.jpg"),
            Pokemon(55, "Golduck", "https://img.pokemondb.net/artwork/golduck.jpg"),
            Pokemon(56, "Mankey", "https://img.pokemondb.net/artwork/mankey.jpg"),
            Pokemon(57, "Primeape", "https://img.pokemondb.net/artwork/primeape.jpg"),
            Pokemon(58, "Growlithe", "https://img.pokemondb.net/artwork/growlithe.jpg"),
            Pokemon(59, "Arcanine", "https://img.pokemondb.net/artwork/arcanine.jpg"),
            Pokemon(60, "Poliwag", "https://img.pokemondb.net/artwork/poliwag.jpg"),
            Pokemon(61, "Poliwhirl", "https://img.pokemondb.net/artwork/poliwhirl.jpg"),
            Pokemon(62, "Poliwrath", "https://img.pokemondb.net/artwork/poliwrath.jpg"),
            Pokemon(63, "Abra", "https://img.pokemondb.net/artwork/abra.jpg"),
            Pokemon(64, "Kadabra", "https://img.pokemondb.net/artwork/kadabra.jpg"),
            Pokemon(65, "Alakazam", "https://img.pokemondb.net/artwork/alakazam.jpg"),
            Pokemon(66, "Machop", "https://img.pokemondb.net/artwork/machop.jpg"),
            Pokemon(67, "Machoke", "https://img.pokemondb.net/artwork/machoke.jpg"),
            Pokemon(68, "Machamp", "https://img.pokemondb.net/artwork/machamp.jpg"),
            Pokemon(69, "Bellsprout", "https://img.pokemondb.net/artwork/bellsprout.jpg"),
            Pokemon(70, "Weepinbell", "https://img.pokemondb.net/artwork/weepinbell.jpg"),
            Pokemon(71, "Victreebel", "https://img.pokemondb.net/artwork/victreebel.jpg"),
            Pokemon(72, "Tentacool", "https://img.pokemondb.net/artwork/tentacool.jpg"),
            Pokemon(73, "Tentacruel", "https://img.pokemondb.net/artwork/tentacruel.jpg"),
            Pokemon(74, "Geodude", "https://img.pokemondb.net/artwork/geodude.jpg"),
            Pokemon(75, "Graveler", "https://img.pokemondb.net/artwork/graveler.jpg"),
            Pokemon(76, "Golem", "https://img.pokemondb.net/artwork/golem.jpg"),
            Pokemon(77, "Ponyta", "https://img.pokemondb.net/artwork/ponyta.jpg"),
            Pokemon(78, "Rapidash", "https://img.pokemondb.net/artwork/rapidash.jpg"),
            Pokemon(79, "Slowpoke", "https://img.pokemondb.net/artwork/slowpoke.jpg"),
            Pokemon(80, "Slowbro", "https://img.pokemondb.net/artwork/slowbro.jpg"),
            Pokemon(81, "Magnemite", "https://img.pokemondb.net/artwork/magnemite.jpg"),
            Pokemon(82, "Magneton", "https://img.pokemondb.net/artwork/magneton.jpg"),
            Pokemon(83, "Farfetch'd", "https://img.pokemondb.net/artwork/farfetchd.jpg"),
            Pokemon(84, "Doduo", "https://img.pokemondb.net/artwork/doduo.jpg"),
            Pokemon(85, "Dodrio", "https://img.pokemondb.net/artwork/dodrio.jpg"),
            Pokemon(86, "Seel", "https://img.pokemondb.net/artwork/seel.jpg"),
            Pokemon(87, "Dewgong", "https://img.pokemondb.net/artwork/dewgong.jpg"),
            Pokemon(88, "Grimer", "https://img.pokemondb.net/artwork/grimer.jpg"),
            Pokemon(89, "Muk", "https://img.pokemondb.net/artwork/muk.jpg"),
            Pokemon(90, "Shellder", "https://img.pokemondb.net/artwork/shellder.jpg"),
            Pokemon(91, "Cloyster", "https://img.pokemondb.net/artwork/cloyster.jpg"),
            Pokemon(92, "Gastly", "https://img.pokemondb.net/artwork/gastly.jpg"),
            Pokemon(93, "Haunter", "https://img.pokemondb.net/artwork/haunter.jpg"),
            Pokemon(94, "Gengar", "https://img.pokemondb.net/artwork/gengar.jpg"),
            Pokemon(95, "Onix", "https://img.pokemondb.net/artwork/onix.jpg"),
            Pokemon(96, "Drowzee", "https://img.pokemondb.net/artwork/drowzee.jpg"),
            Pokemon(97, "Hypno", "https://img.pokemondb.net/artwork/hypno.jpg"),
            Pokemon(98, "Krabby", "https://img.pokemondb.net/artwork/krabby.jpg"),
            Pokemon(99, "Kingler", "https://img.pokemondb.net/artwork/kingler.jpg"),
            Pokemon(100, "Voltorb", "https://img.pokemondb.net/artwork/voltorb.jpg")
        )

        adapter = PokemonAdapter(this, pokemonList)
        recyclerView.adapter = adapter
    }
}