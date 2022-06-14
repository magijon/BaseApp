package com.mgijon.baseapp.example.model

import com.mgijon.domain.model.marvel.Character

data class CharacterUI(
    val id : String,
    val name : String,
    val image : String,
    val description : String?,
    val visible : Boolean = true
)
{
    companion object{
        fun mapperCharacterUI(character : Character) : CharacterUI =
            character.let {
                CharacterUI(it.id, it.name, it.image, it.description)
            }
    }
}
