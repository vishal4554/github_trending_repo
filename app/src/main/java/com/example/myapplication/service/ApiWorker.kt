package com.example.myapplication.service

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.database.Database
import com.example.myapplication.database.Repos
import com.example.myapplication.model.ListResponse
import com.example.myapplication.model.RepositoryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ApiWorker(private val context: Context, private val workerParameters: WorkerParameters) : CoroutineWorker(context,workerParameters) {
    private var apiClient : ApiClient = ApiClient()

    override suspend fun doWork(): Result {
        callApi()
        return Result.success()
    }

    private fun callApi() {
        apiClient
            .api
            .getAllRepository()
            .enqueue(object : retrofit2.Callback<ListResponse?> {
                override fun onResponse(
                    call: Call<ListResponse?>,
                    response: Response<ListResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        storeToDatabase(response.body()?.items)
                    } else
                        Log.d("TAG", "onResponse: ")

                }


                override fun onFailure(call: Call<ListResponse?>, t: Throwable) {
                    Log.d("TAG", "onFailure: ${t.localizedMessage}")
                }

            })


    }

    private fun storeToDatabase(items: List<RepositoryItem?>?) {
        CoroutineScope(Dispatchers.Default).launch{
            Database.getDatabaseInstance(context).ReposDao().deleteAll()
            items?.let {
                for (i in it){
                    val repos= Repos(
                        reposId = i?.id.toString(),
                        name = i?.name?:"",
                        login=i?.owner?.login?:"",
                        description = i?.description?:"",
                        image = i?.owner?.avatarUrl ?:"",
                        language = i?.language?:""
                    )
                    Database.getDatabaseInstance(context).ReposDao().addRepos(repos)
                }
            }
        }

    }


}