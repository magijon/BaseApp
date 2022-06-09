package com.mgijon.data.extensions

import com.mgijon.domain.model.CharacterModel
import com.mgijon.data.model.Character

fun CharacterModel.toCharacter(): Character =
    Character(id.toString(), name.toString(), "${thumbnail?.path.toString()}.${thumbnail?.extension}",
    description)