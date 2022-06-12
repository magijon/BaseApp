package com.mgijon.marvelapi.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mgijon.marvelapi.persistence.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characterEntity")
    fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characterEntity WHERE id = (:characterId)")
    fun getOne(characterId : String): CharacterEntity

    @Query("SELECT * FROM characterEntity WHERE id IN (:charactersId)")
    fun loadAllByIds(charactersId: List<String>): List<CharacterEntity>

    @Insert
    fun insertAll(characterEntity: List<CharacterEntity>)

    @Delete
    fun delete(characterEntity: CharacterEntity)
}