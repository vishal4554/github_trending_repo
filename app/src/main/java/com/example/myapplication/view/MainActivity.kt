package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.example.myapplication.database.Repos
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.service.ApiWorker
import com.example.myapplication.utills.Common
import com.example.myapplication.view.adapter.ListAdapter
import com.example.myapplication.viewmodel.MainViewModel
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity(), ListAdapter.OnCardClick {
    private lateinit var binding: ActivityMainBinding
    private var listAdapter: ListAdapter? = null
    private var repositoryItemList:MutableList<Repos> =ArrayList()
    private val mainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private var doAsynchronousTask: TimerTask? = null
    private var timer: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        observeViewModel()
        if (Common.isNetworkAvailable()) {
            setPeriodicWork()
        }
    }

    private fun setPeriodicWork() {
         val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val period=PeriodicWorkRequest.Builder(ApiWorker::class.java,15,TimeUnit.MINUTES)
            .addTag("periodic_api_call")
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("periodic_api_call",ExistingPeriodicWorkPolicy.KEEP,period)
    }


    private fun observeViewModel() {

        mainViewModel.getRepos.observe(this){
            it?.let {
                repositoryItemList.clear()
                repositoryItemList.addAll(it)
                listAdapter?.notifyDataSetChanged()
            }
        }

    }

    private fun initRecyclerView() {
        binding.mainRv.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        listAdapter= ListAdapter(this,repositoryItemList,this)
        binding.mainRv.adapter=listAdapter
    }

    override fun onClick(items: Repos) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("reposDetail", items)
        startActivity(intent)
    }


}