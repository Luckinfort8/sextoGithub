package com.example.pokeapi

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // Declarar las vistas
    private lateinit var txtPokemonName: TextView
    private lateinit var txtPokemonNumber: TextView
    private lateinit var txtPokemonHeight: TextView
    private lateinit var txtPokemonWeight: TextView
    private lateinit var imgPokemon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas
        txtPokemonName = findViewById(R.id.txtPokemonName)
        txtPokemonNumber = findViewById(R.id.txtPokemonNumber)
        txtPokemonHeight = findViewById(R.id.txtPokemonHeight)
        txtPokemonWeight = findViewById(R.id.txtPokemonWeight)
        imgPokemon = findViewById(R.id.imgPokemon)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PokemonService::class.java)
        val randomPokemonId = (1..151).random() // Obtener un Pokémon aleatorio del rango 1-151

        val call = service.getPokemonDetails(randomPokemonId)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    pokemon?.let {
                        txtPokemonName.text = it.name
                        txtPokemonNumber.text = "Número: ${it.id}"
                        txtPokemonHeight.text = "Altura: ${it.height} dm"
                        txtPokemonWeight.text = "Peso: ${it.weight} hg"
                        Picasso.get().load(it.sprites.front_default).into(imgPokemon)
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                // Manejar errores de la llamada a la API aquí
            }
        })
    }
}