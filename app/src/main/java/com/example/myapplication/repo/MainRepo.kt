package com.example.myapplication.repo

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.database.Database
import com.example.myapplication.database.Repos
import com.example.myapplication.database.ReposDao
import com.example.myapplication.model.ListResponse
import com.example.myapplication.model.RepositoryItem
import com.example.myapplication.service.ApiClient
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainRepo(private val reposDao: ReposDao) {

    val getAllRepos : LiveData<List<Repos>> = reposDao.getAll()

    suspend fun addRepos(movies: Repos){
        reposDao.addRepos(movies)
    }

    fun delete(){
        reposDao.deleteAll()
    }



}