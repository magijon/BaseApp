package com.mgijon.marvelapi.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mgijon.data.model.Character

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val key: Int = 0,
    val id: String,
    val name: String,
    val image: String,
    val description: String?,
    val visible : Boolean = true
) {
    fun characterMapper(): Character = Character(
        id,
        name,
        image,
        description,
        visible
    )

    companion object {
        fun characterEntityMapper(character: Character): CharacterEntity = CharacterEntity(
            id = character.id,
            name = character.name,
            image = character.image,
            description = character.description
        )
    }
}