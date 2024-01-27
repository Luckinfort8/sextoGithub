package com.example.pokeapi3

import retrofit2.Call
import retrofit2.http.*

interface PokemonService {
    @GET("Pokemon/")
    fun getPokemonList(): Call<List<Pokemon>>

    @GET("Pokemon/{id}/")
    fun getPokemonDetails(@Path("id") id: Int): Call<Pokemon>

    @POST("Pokemon/")
    fun createPokemon(@Body pokemon: Pokemon): Call<Pokemon>

    @PUT("Pokemon/{id}/")
    fun updatePokemon(@Path("id") id: Int, @Body pokemon: Pokemon): Call<Pokemon>

    @DELETE("Pokemon/{id}/")
    fun deletePokemon(@Path("id") id: Int): Call<Void>
}