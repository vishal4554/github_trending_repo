package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReposDao {

    @Query("SELECT * FROM Repository")
    fun getAll(): LiveData<List<Repos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRepos(movies: Repos)

    @Query("DELETE FROM Repository")
    fun deleteAll()
}