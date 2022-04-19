package com.mgijon.baseapp.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mgijon.baseapp.R
import com.mgijon.baseapp.databinding.ItemListCharactersBinding
import com.mgijon.domain.model.Character

class AdapterListCharacter : RecyclerView.Adapter<ViewHolderListCharacter>() {

    private val list: MutableList<Character> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListCharacter {
        val binding = ItemListCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderListCharacter(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderListCharacter, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateCharacters(characters: MutableList<Character>?) {
        characters?.map {
            list.add(it)
            notifyItemChanged(list.size - 1)
        }
    }
}

class ViewHolderListCharacter(private val item: ItemListCharactersBinding) : RecyclerView.ViewHolder(item.root) {
    fun onBind(characterModelUI: Character) {
        item.apply {
            tvName.text = characterModelUI.name
            Glide.with(item.root.context)
                .load(characterModelUI.image)
                .placeholder(R.drawable.iron_man)
                .into(ivCharacter)
        }
    }
}