package com.caitlykate.notebook.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.PopupMenu
import com.caitlykate.notebook.R
import com.caitlykate.notebook.databinding.ActivityMainBinding
import com.caitlykate.notebook.fragments.FragmentManager
import com.caitlykate.notebook.fragments.NoteFragment


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        setButtonNavViewListener()
    }

    private fun setButtonNavViewListener(){
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.id_settings -> {}
                R.id.id_notes -> {
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.id_shop_lists -> {}
                R.id.id_new -> {showPopup()}
            }
            true
        }
    }


    private fun showPopup(){
        // Вариант с поддержкой значков
        val popupMenu2 = PopupMenu(this, binding.bNav, Gravity.END)
        popupMenu2.inflate(R.menu.popup_menu)

        popupMenu2.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.new_note -> {
                    FragmentManager.currentFrag?.onClickNew()
                }
                R.id.new_checklist -> {

                }
            }
            false
        }

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu2.setForceShowIcon(true)
        }
        popupMenu2.show()
/*
        button.setOnClickListener {

        }*/
    }
}