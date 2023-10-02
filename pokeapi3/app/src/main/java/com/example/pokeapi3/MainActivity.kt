package com.example.pokeapi3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtAltura: EditText
    private lateinit var txtPeso: EditText

    private lateinit var btnGet: Button
    private lateinit var btnPost: Button
    private lateinit var btnPut: Button
    private lateinit var btnDelete: Button

    private lateinit var apiService: PokemonService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtId = findViewById(R.id.editTextId)
        txtNombre = findViewById(R.id.editTextNombre)
        txtAltura = findViewById(R.id.editTextAltura)
        txtPeso = findViewById(R.id.editTextPeso)

        btnGet = findViewById(R.id.btnGet)
        btnPost = findViewById(R.id.btnPost)
        btnPut = findViewById(R.id.btnPut)
        btnDelete = findViewById(R.id.btnDelete)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.23.113:8000/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(PokemonService::class.java)

        btnGet.setOnClickListener {
            val id = txtId.text.toString()
            if (id.isNotBlank()) {
                getPokemonDetails(id.toInt())
            } else {
                Toast.makeText(this@MainActivity, "El campo ID no debe estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        btnPost.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val alturaText = txtAltura.text.toString()
            val pesoText = txtPeso.text.toString()

            if (nombre.isNotBlank() && alturaText.isNotBlank() && pesoText.isNotBlank()) {
                val altura = alturaText.toInt()
                val peso = pesoText.toInt()
                val nuevoPokemon = Pokemon(null, nombre, altura, peso)
                createPokemon(nuevoPokemon)
            } else {
                Toast.makeText(this@MainActivity, "Los campos Nombre, Altura y Peso no deben estar vacíos", Toast.LENGTH_SHORT).show()
            }
        }

        btnPut.setOnClickListener {
            val id = txtId.text.toString()
            val nombre = txtNombre.text.toString()
            val alturaText = txtAltura.text.toString()
            val pesoText = txtPeso.text.toString()

            if (id.isNotBlank() && nombre.isNotBlank() && alturaText.isNotBlank() && pesoText.isNotBlank()) {
                val altura = alturaText.toInt()
                val peso = pesoText.toInt()
                val pokemonActualizado = Pokemon(id.toInt(), nombre, altura, peso)
                updatePokemon(id.toInt(), pokemonActualizado)
            } else {
                Toast.makeText(this@MainActivity, "Todos los campos deben estar llenos para actualizar", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            val id = txtId.text.toString()
            if (id.isNotBlank()) {
                deletePokemon(id.toInt())
            } else {
                Toast.makeText(this@MainActivity, "El campo ID no debe estar vacío para eliminar", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun getPokemonDetails(id: Int) {
        val call = apiService.getPokemonDetails(id)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()
                    txtNombre.setText(pokemon?.nombre)
                    txtAltura.setText(pokemon?.altura.toString())
                    txtPeso.setText(pokemon?.peso.toString())
                } else {
                    Toast.makeText(this@MainActivity, "Error al obtener los datos del Pokémon", Toast.LENGTH_SHORT).show()
                    //Imprimir en la consola el error
                    println(response.errorBody())
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                //Imprimir en la consola el error
                t.printStackTrace()
            }
        })
    }

    private fun createPokemon(pokemon: Pokemon) {
        val call = apiService.createPokemon(pokemon)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Pokémon creado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error al crear el Pokémon", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                //Imprimir en la consola el error
                t.printStackTrace()
            }
        })
    }

    private fun updatePokemon(id: Int, pokemon: Pokemon) {
        val call = apiService.updatePokemon(id, pokemon)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Pokémon actualizado con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error al actualizar el Pokémon", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                //Imprimir en la consola el error
                t.printStackTrace()
            }
        })
    }

    private fun deletePokemon(id: Int) {
        val call = apiService.deletePokemon(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Pokémon eliminado con éxito", Toast.LENGTH_SHORT).show()
                    txtNombre.text.clear()
                    txtAltura.text.clear()
                    txtPeso.text.clear()
                } else {
                    Toast.makeText(this@MainActivity, "Error al eliminar el Pokémon", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                //Imprimir en la consola el error
                t.printStackTrace()
            }
        })
    }
}