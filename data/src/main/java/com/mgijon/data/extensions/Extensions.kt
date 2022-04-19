package com.mgijon.data.extensions

import com.mgijon.data.model.CharacterModel
import com.mgijon.domain.model.Character

fun CharacterModel.toCharacter(): Character =
    Character(id.toString(), name.toString(), "${thumbnail?.path.toString()}.${thumbnail?.extension}")