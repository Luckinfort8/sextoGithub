package com.example.pokeapi2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/{id}/")
    fun getPokemonDetails(@Path("id") id: Int): Call<Pokemon>
}
