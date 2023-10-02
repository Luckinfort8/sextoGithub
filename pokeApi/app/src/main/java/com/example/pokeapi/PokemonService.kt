package com.example.pokeapi

import retrofit2.Call
import retrofit2.http.GET
interface PokemonService {
    @GET("pokemon/{id}")
    fun getPokemonDetails(randomPokemonId: Int): Call<Pokemon>
}