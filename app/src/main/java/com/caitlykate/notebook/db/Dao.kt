package com.caitlykate.notebook.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.entities.ShoppingListName
import kotlinx.coroutines.flow.Flow

//data access object
@Dao
interface Dao {

    @Insert
    suspend fun insertNote(note: NoteItem)

    @Insert
    suspend fun insertShopListName(name: ShoppingListName)

    @Query (value = "SELECT * FROM note_list")
    //Flow, поэтому не нужно указывать suspend
    fun getAllNotes(): Flow<List<NoteItem>>       //спец. класс-поток в корутинах, который будет подключать БД к нашему списку и автоматически все обновлять

    @Query (value = "SELECT * FROM shopping_list_names")
    fun getAllListsNames(): Flow<List<ShoppingListName>>

    @Query (value = "DELETE FROM note_list WHERE ID IS :id")
    suspend fun deleteNote(id: Int)

    @Query (value = "DELETE FROM shopping_list_names WHERE ID IS :id")
    suspend fun deleteShopListName(id: Int)

    @Update
    suspend fun updateNote(note: NoteItem)
}