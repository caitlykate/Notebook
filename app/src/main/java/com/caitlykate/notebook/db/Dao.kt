package com.caitlykate.notebook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.caitlykate.notebook.entities.NoteItem
import kotlinx.coroutines.flow.Flow

//data access object
@Dao
interface Dao {

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Query (value = "SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>       //спец. класс-поток в корутинах, который будет подключать БД к нашему списку и автоматически все обновлять
}