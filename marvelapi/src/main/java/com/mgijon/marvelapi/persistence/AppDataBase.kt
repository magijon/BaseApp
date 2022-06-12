package com.mgijon.marvelapi.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mgijon.marvelapi.persistence.dao.CharacterDao
import com.mgijon.marvelapi.persistence.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}