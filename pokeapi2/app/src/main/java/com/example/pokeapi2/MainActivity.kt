package com.example.pokeapi2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var txtPokemonName: TextView
    private lateinit var txtPokemonNumber: TextView
    private lateinit var txtPokemonHeight: TextView
    private lateinit var txtPokemonWeight: TextView
    private lateinit var imgPokemon: ImageView
    private lateinit var btnNextPokemon: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtPokemonName = findViewById(R.id.txtPokemonName)
        txtPokemonNumber = findViewById(R.id.txtPokemonNumber)
        txtPokemonHeight = findViewById(R.id.txtPokemonHeight)
        txtPokemonWeight = findViewById(R.id.txtPokemonWeight)
        imgPokemon = findViewById(R.id.imgPokemon)
        btnNextPokemon = findViewById(R.id.btnNextPokemon)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PokemonService::class.java)

        // Función para obtener un Pokémon aleatorio y actualizar la interfaz de usuario
        fun getRandomPokemon() {
            val randomPokemonId = (1..151).random() // Obtener un Pokémon aleatorio del rango 1-151

            val call = service.getPokemonDetails(randomPokemonId)
            call.enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.isSuccessful) {
                        val pokemon = response.body()
                        pokemon?.let {
                            txtPokemonName.text = it.name
                            txtPokemonNumber.text = "Número: ${it.id}"
                            txtPokemonHeight.text = "Altura: ${it.height*10} cm"
                            txtPokemonWeight.text = "Peso: ${it.weight/10} kg"
                            Picasso.get().load(it.sprites.front_default).into(imgPokemon)
                        }
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    // Manejar errores de la llamada a la API aquí
                }
            })
        }

        // Configurar un OnClickListener para el botón "Siguiente Pokémon"
        btnNextPokemon.setOnClickListener {
            getRandomPokemon()
        }

        // Obtener y mostrar un Pokémon aleatorio al iniciar la actividad
        getRandomPokemon()
    }
}
