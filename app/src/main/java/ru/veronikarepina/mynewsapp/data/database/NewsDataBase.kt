package ru.veronikarepina.mynewsapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.veronikarepina.mynewsapp.model.Article

@Database (entities = [Article::class], version = 2, exportSchema = false)
abstract class NewsDataBase: RoomDatabase() {
    abstract fun newsDao(): Dao
    companion object{
        private var INSTANCE: NewsDataBase? = null
        fun getDataBase(context: Context): NewsDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDataBase::class.java,
                    "NewsDataBase"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}