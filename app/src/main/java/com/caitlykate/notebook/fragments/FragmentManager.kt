package com.caitlykate.notebook.fragments

import androidx.appcompat.app.AppCompatActivity
import com.caitlykate.notebook.R

object FragmentManager {        //без инициализации

    var currentFrag: BaseFragment? = null

    //для переключения м/у фрагментами
    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity){
        //спец класс который будет переключать фрагменты
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }
}