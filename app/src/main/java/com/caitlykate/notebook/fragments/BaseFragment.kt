package com.caitlykate.notebook.fragments

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {   //полиморфизм
    abstract fun onClickNew()
}