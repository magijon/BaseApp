package com.mgijon.data.extensions

import com.mgijon.data.model.CharacterModel
import com.mgijon.data.persistence.entity.CharacterEntity
import com.mgijon.domain.model.marvel.Character

fun CharacterModel.toCharacter(): Character =
    Character(id.toString(), name.toString(), "${thumbnail?.path.toString()}.${thumbnail?.extension}", description)

fun CharacterEntity.toCharacter(): Character =
    Character(id, name, image, description)

fun CharacterModel.toCharacterEntity(): CharacterEntity = CharacterEntity(
    id = id.toString(), name = name.toString(), image = "${thumbnail?.path.toString()}.${thumbnail?.extension}", description = description
)