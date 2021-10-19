package com.caitlykate.notebook.activities

import android.app.Application
import com.caitlykate.notebook.db.MainDataBase

class MainApp: Application() {
    val database by lazy { MainDataBase.getDataBase(this) } //код запускается один раз
}