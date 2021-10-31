package com.caitlykate.notebook.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.caitlykate.notebook.activities.MainApp
import com.caitlykate.notebook.db.MainDataBase
import com.caitlykate.notebook.entities.NoteItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(dataBase: MainDataBase): ViewModel() {

    private val dao = dataBase.getDao()

    //когда список будет меняться, LiveData автоматически обновится
    val allNotes: LiveData<List<NoteItem>>  = dao.getAllNotes().asLiveData()    //один раз достаем из БД у что мы подключили Flow
    //во view подключим обсервер, кот. следит за изменениями, там будет запускаться ф-я

    fun insertNote(note: NoteItem) = viewModelScope.launch {            //в корутине
        dao.insertNote(note)
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