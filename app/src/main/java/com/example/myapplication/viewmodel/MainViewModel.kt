package com.example.myapplication.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.myapplication.database.Database
import com.example.myapplication.database.Repos
import com.example.myapplication.model.ListResponse
import com.example.myapplication.model.RepositoryItem
import com.example.myapplication.repo.MainRepo
import com.example.myapplication.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(app: Application) :AndroidViewModel(app) {

   lateinit var getRepos : LiveData<List<Repos>>
    private lateinit var  mainRepo: MainRepo
    private var getJob: Job?= null
    private lateinit var apiClient : ApiClient


    init {
        getJob= Job()
        val moviesDao =  Database.getDatabaseInstance(app).ReposDao()
        mainRepo = MainRepo(moviesDao)
        apiClient = ApiClient()
        getRepos=mainRepo.getAllRepos
    }

    fun getAllRepos(){

        apiClient
            .api
            .getAllRepository()
            .enqueue(object : retrofit2.Callback<ListResponse?> {
                override fun onResponse(
                    call: Call<ListResponse?>,
                    response: Response<ListResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        addToDatabase(response.body()?.items)
                        Log.d("TAG", "onReseponse: Success")
                    } else if (response.code() == 400 && response.errorBody() != null) {
                        Log.d("TAG", "onResponse: ")
                    }
                }


                override fun onFailure(call: Call<ListResponse?>, t: Throwable) {
                    Log.d("TAG", "onFailure: ${t.localizedMessage}")
                }

            })
    }

    fun addToDatabase(items: List<RepositoryItem?>?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (items!=null){
                mainRepo.delete()
                for (i in items){
                    val repos=Repos(
                        reposId = i?.id.toString(),
                        name = i?.name?:"",
                        login=i?.owner?.login?:"",
                        description = i?.description?:"",
                        image = i?.owner?.avatarUrl ?:"",
                        language = i?.language?:""
                    )
                    mainRepo.addRepos(repos)
                }
                getRepos=mainRepo.getAllRepos
            }
        }


    }

}