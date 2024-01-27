package com.example.pokeapi2

data class Pokemon(
    val name: String,
    val sprites: Sprites,
    val id: Int,
    val height: Int,
    val weight: Int
)

data class Sprites(
    val front_default: String
)