package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.database.Repos
import com.example.myapplication.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
        private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Details Screen"
        initUi()
    }

    private fun initUi() {
        if (intent.hasExtra("reposDetail")) {
            val data = intent.getParcelableExtra<Repos>("reposDetail")
            Glide.with(this)
                .load(data?.image)
                .centerCrop()
                .into(binding.profileImage)

            binding.textView.text = data?.name
            binding.loginTv.text = "Login Name :${data?.login}"
            binding.descriptionTv.text = "Description :${data?.description}"
            binding.languageTv.text = "Language: ${data?.language}"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }


}