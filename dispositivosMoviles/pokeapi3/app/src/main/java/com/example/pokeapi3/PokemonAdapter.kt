package com.example.pokeapi3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    private var pokemonList: List<Pokemon> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.bind(pokemon)
    }

    fun setPokemonList(pokemonList: List<Pokemon>) {
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)

        fun bind(pokemon: Pokemon) {
            idTextView.text = "ID: ${pokemon.id}"
            nameTextView.text = "Nombre: ${pokemon.nombre}"
        }
    }
}
