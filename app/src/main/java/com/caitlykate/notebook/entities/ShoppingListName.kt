package com.caitlykate.notebook.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "shopping_list_names")
data class ShoppingListName(

    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "name")
    val name: String,

    @ColumnInfo (name = "time")
    val time: String,

    @ColumnInfo (name = "allItemsCount")
    val allItemsCount: Int,

    @ColumnInfo (name = "checkedItemsCount")
    val checkedItemsCount: Int,

    @ColumnInfo (name = "itemsIds")
    val itemsIds: String,
): Serializable
