package com.caitlykate.notebook.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.caitlykate.notebook.entities.LibraryItem
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.entities.ShoppingListItem
import com.caitlykate.notebook.entities.ShoppingListNames

@Database (entities = [LibraryItem::class, NoteItem::class,
    ShoppingListItem::class, ShoppingListNames::class],
    version = 1,
)
abstract class MainDataBase : RoomDatabase() {

    companion object {
        @Volatile           //переменная мгновенно становится доступна для остальных потоков
        private var INSTANCE: MainDataBase? = null      //эту переменную будем возвращать когда запрашиваем БД
        fun getDataBase(context: Context): MainDataBase{
            return INSTANCE ?: synchronized(this){     //если несколько потоков пытаются запустить, то выполняем только в первом, в скобках чем блокируем (этим классом)
                val instance = Room.databaseBuilder(        //создаем файл с БД и получаем на нее ссылку
                    context.applicationContext,
                    MainDataBase::class.java,
                "notebook.db").build()
                instance
            }
        }
    }
}