package com.mgijon.data.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mgijon.data.persistence.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characterEntity")
    fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characterEntity WHERE id = (:characterId)")
    fun getOne(characterId : String): CharacterEntity

    @Query("SELECT * FROM characterEntity WHERE name LIKE '%' || :name || '%'")
    fun getFilter(name : String): List<CharacterEntity>

    @Query("SELECT * FROM characterEntity WHERE id IN (:charactersId)")
    fun loadAllByIds(charactersId: List<String>): List<CharacterEntity>

    @Query("UPDATE characterEntity SET visible = 'FALSE' WHERE id = (:characterId) ")
    fun removeVisibility(characterId : String)

    @Insert
    fun insertAll(characterEntity: List<CharacterEntity>)

    @Delete
    fun delete(characterEntity: CharacterEntity)
}