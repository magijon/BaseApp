package com.mgijon.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mgijon.data.persistence.dao.CharacterDao
import com.mgijon.data.persistence.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}