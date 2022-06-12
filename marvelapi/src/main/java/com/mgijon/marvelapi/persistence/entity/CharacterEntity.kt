package com.mgijon.marvelapi.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mgijon.data.model.Character

@Entity
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val image: String,
    val description: String?
) {
    fun characterMapper(): Character = Character(
        id,
        name,
        image,
        description
    )

    companion object{
        fun characterEntityMapper(character: Character): CharacterEntity = CharacterEntity(
            character.id,
            character.name,
            character.image,
            character.description
        )
    }
}