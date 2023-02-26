package ru.veronikarepina.mynewsapp

import android.app.Application
import ru.veronikarepina.mynewsapp.data.DataObject

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DataObject.initData(this)
    }
}