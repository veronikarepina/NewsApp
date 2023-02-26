package ru.veronikarepina.mynewsapp.data.database
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import ru.veronikarepina.mynewsapp.model.Article

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewDao(new: Article)
    @Query("SELECT * FROM news_table")
    fun getAllNews(): LiveData<List<Article>>
    @Query("SELECT COUNT (*) FROM news_table")
    suspend fun checkEmpty(): Int
    @Delete
    suspend fun deleteNewDao(new: Article)
    @Query("SELECT flag FROM news_table WHERE title = :title")
    fun getFlag(title: String?): Int
    @Query("SELECT * FROM news_table WHERE title = :title")
    fun searchNew(title: String?): Article
}