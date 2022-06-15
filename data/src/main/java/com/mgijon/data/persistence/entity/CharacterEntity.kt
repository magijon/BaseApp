package com.mgijon.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val key: Int = 0,
    val id: String,
    val name: String,
    val image: String,
    val description: String?,
    val visible: Boolean = true
)