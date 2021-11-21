package com.caitlykate.notebook.viewmodel

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.caitlykate.notebook.activities.MainApp
import com.caitlykate.notebook.db.MainDataBase
import com.caitlykate.notebook.entities.NoteItem
import com.caitlykate.notebook.entities.ShoppingListName
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(dataBase: MainDataBase): ViewModel() {

    private val dao = dataBase.getDao()

    //когда список будет меняться, LiveData автоматически обновится
    val allNotes: LiveData<List<NoteItem>>  = dao.getAllNotes().asLiveData()    //один раз достаем из БД потому что мы подключили Flow
    //во view подключим обсервер, кот. следит за изменениями, там будет запускаться ф-я

    val allListsNames: LiveData<List<ShoppingListName>>  = dao.getAllListsNames().asLiveData()

    fun insertNote(note: NoteItem) = viewModelScope.launch {            //в корутине
        dao.insertNote(note)
    }

    fun insertShopListName(listName: ShoppingListName) = viewModelScope.launch {            //в корутине
        dao.insertShopListName(listName)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {            //в корутине
        dao.updateNote(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {            //в корутине
        dao.deleteNote(id)
    }

    fun deleteShopListName(id: Int) = viewModelScope.launch {            //в корутине
        dao.deleteShopListName(id)
    }

    //получать доступ к ViewModel мы будем через делегата (спец. метод viewModels)
    //и в него нужно передать (если конструктор VM не пустой) в качестве аргумента фабрику VM:
    //так рекомендуют делать на оф сайте
    class MainViewModelFactory(private val dataBase: MainDataBase): ViewModelProvider.Factory {
        //в качестве аргумента приходит класс VM и в результат мы должны передать уже саму созданную VM
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            //если может быть передан
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){        //или if (modelClass == MainViewModel::class.java)
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(dataBase) as T                 //нужно привести тип к дженерику Т
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}

fun Fragment.factory() = MainViewModel.MainViewModelFactory((requireContext().applicationContext as MainApp).database)

fun Activity.factory() = MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)